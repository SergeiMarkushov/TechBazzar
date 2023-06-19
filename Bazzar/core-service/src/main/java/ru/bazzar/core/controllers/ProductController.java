package ru.bazzar.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.core.api.PageDto;
import ru.bazzar.core.api.PictureDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.converters.ProductConverter;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.integrations.OrganizationServiceIntegration;
import ru.bazzar.core.integrations.PictureServiceIntegration;
import ru.bazzar.core.services.ProductService;
import ru.bazzar.core.utils.MyQueue;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")

public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final OrganizationServiceIntegration organizationService;
    private final PictureServiceIntegration pictureServiceIntegration;

    private MyQueue<Product> productQueue = new MyQueue<>();//думаю что временно не используется


    @GetMapping("/not_confirmed")
    public ProductDto notConfirmed() throws ResourceNotFoundException {
        return productConverter.entityToDto(productService.notConfirmed());
    }

    @GetMapping("/confirm/{title}")
    public void confirm(@PathVariable @NotBlank String title) {
        productService.confirm(title);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public PageDto<ProductDto> getProductDtosPage(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "characteristic_part", required = false) String characteristicPart,
            @RequestParam(name = "organization_title", required = false) String organizationTitle,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "limit", defaultValue = "20") int limit
    ) {
        if (page < 1) {
            page = 1;
        }


        Page<ProductDto> jpaPage = productService.find(minPrice, maxPrice, titlePart, organizationTitle, page, characteristicPart, limit).map(
                productConverter::entityToDto
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

    @GetMapping("/{id}")
    public ProductDto getProductDto(@PathVariable @Min(0) Long id) {
        return productConverter.entityToDto(productService.findById(id));
    }

    @GetMapping("/find-pic-dto/{id}")
    public PictureDto getPictureDtoById(@PathVariable Long id) {
        return pictureServiceIntegration.getPictureDtoById(id);
    }

    @PostMapping(value = "/save-pic-return-id", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long savePicAndReturnId(@RequestParam(value = "multipart-pic") MultipartFile pic) throws IOException{
        PictureDto pictureDto = new PictureDto();
        pictureDto.setFileName(pic.getOriginalFilename());
        pictureDto.setContentType(pic.getContentType());
        pictureDto.setBytes(pic.getBytes());
        return pictureServiceIntegration.savePictureDtoAndReturnId(pictureDto);
    }

    //кладёт в кэш...
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductDto createProduct(
            @RequestHeader String username,
            @RequestParam(value = "productDto") String product,
            @RequestParam(value = "product_picture", required = false) MultipartFile multipartFile)
            throws IOException {
        //если подгружена картинка - назначаем productDto.setPicture_id
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(product, ProductDto.class);

        if (multipartFile == null) {
            productDto.setPictureId(1L);
        } else {
            productDto = productService.setProductPicture(productDto, multipartFile);
        }
        return productConverter.entityToDto(productService.createProduct(productDto, username));
    }

    //обновляет кэш...
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto updateProduct(
            @RequestHeader String username,
            @RequestParam(value = "productDto") String product,
            @RequestParam(value = "product_picture", required = false) MultipartFile multipartFile)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(product, ProductDto.class);
        //если есть multipartFile картинка - удаляем старую, подгружаем новую
        if (multipartFile != null) {
            if (productDto.getPictureId() == null || productDto.getPictureId() < 0) {
                productDto.setPictureId(1L);
            } else {
                pictureServiceIntegration.deletePictureById(productDto.getPictureId());
                productDto = productService.setProductPicture(productDto, multipartFile);
            }

        }
        return productConverter.entityToDto(productService.updateProduct(productDto, productDto.getId()));
    }
}