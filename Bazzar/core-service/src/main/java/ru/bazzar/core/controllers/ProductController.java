package ru.bazzar.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.core.api.PageDto;
import ru.bazzar.core.api.PictureDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.api.UserDto;
import ru.bazzar.core.converters.ProductConverter;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.integrations.OrganizationServiceIntegration;
import ru.bazzar.core.integrations.PictureServiceIntegration;
import ru.bazzar.core.integrations.UserServiceIntegration;
import ru.bazzar.core.services.ProductService;
import ru.bazzar.core.utils.MyQueue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")

public class ProductController {
    private final ProductService service;
    private final ProductConverter converter;
    private final OrganizationServiceIntegration organizationService;
    private final PictureServiceIntegration pictureService;
    private final UserServiceIntegration userService;
    private MyQueue<Product> productQueue = new MyQueue<>();//думаю что временно не используется
    private final ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/not_confirmed")
    public ProductDto notConfirmed() throws ResourceNotFoundException {
        return converter.entityToDto(service.notConfirmed());
    }

    @GetMapping("/confirm/{title}")
    public void confirm(@PathVariable @NotBlank String title) {
        service.confirm(title);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping
    public PageDto<ProductDto> getProductDtosPage(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "full_name") String fullName,
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "characteristic_part", required = false) String characteristicPart,
            @RequestParam(name = "organization_title", required = false) String organizationTitle,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "limit", defaultValue = "20") int limit
    ) {
//        String fullName1 = URLDecoder.decode(fullName, StandardCharsets.UTF_8);
        System.out.println(username);
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setFullName(fullName);
        userService.checkAndCreate(userDto);

        if (page < 1) {
            page = 1;
        }

        Page<ProductDto> jpaPage = service.find(minPrice, maxPrice, titlePart, organizationTitle, page, characteristicPart, limit).map(
                converter::entityToDto
        );
        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(jpaPage.getNumber());
        out.setTotalPages(jpaPage.getTotalPages());
        List<ProductDto> productDtos = new ArrayList<>();
        for (ProductDto productDto : jpaPage.getContent()) {
            if (organizationService.isOrgActive(productDto.getOrganizationTitle())) {
                if (productDto.isConfirmed()) {
                    productDtos.add(productDto);
                }
            }
        }
        out.setItems(productDtos);
        return out;
    }

    @GetMapping("/{id}")//
    public ProductDto getProductDto(@PathVariable @Min(0) Long id) {
        return converter.entityToDto(service.findById(id));
    }

    @GetMapping("/picture/{id}") //"/find-pic-dto/{id}"
    public PictureDto getPictureDtoById(@PathVariable Long id) {
        return pictureService.getPictureDtoById(id);
    }

    @GetMapping("/picture-by-product/{id}")
    public ResponseEntity<PictureDto> getPictureByProductId(@PathVariable Long id) {
        return service.getPictureByProductId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto createProduct(
            @RequestHeader String username,
            @RequestParam(value = "productDto") String product,
            @RequestParam(value = "product_picture") MultipartFile multipartFile)
            throws IOException {
        System.out.println(username);
        ProductDto productDto = objectMapper.readValue(product, ProductDto.class);
        productDto = service.setProductPicture(productDto, multipartFile);
        return converter.entityToDto(service.createProduct(productDto, username));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto updateProduct(
            @RequestParam(value = "productDto") String product,
            @RequestParam(value = "product_picture", required = false) MultipartFile multipartFile)
            throws IOException {

        ProductDto productDto = objectMapper.readValue(product, ProductDto.class);

        if (multipartFile != null) {
            productDto = service.setProductPicture(productDto, multipartFile);

        }
        return converter.entityToDto(service.updateProduct(productDto, productDto.getId()));
    }

    @GetMapping("/{productId}/average-rating")
    public ResponseEntity<Double> getProductAverageRating(@PathVariable Long productId) {
        Double averageRating = service.calculateAverageRating(productId);
        return ResponseEntity.ok(averageRating);
    }
}