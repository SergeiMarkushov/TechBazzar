package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.core.api.*;
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
    private MyQueue<Product> productQueue = new MyQueue<>();//?


    @GetMapping
    public PageDto<ProductDto> getProductDtosPage(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            //@RequestParam(name = "keyword_part", required = false) String keywordPart,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }

        Page<ProductDto> jpaPage = productService.find(minPrice, maxPrice, titlePart, page).map(
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

    //получение json PictureDto(может понадобится..)
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

    //получает и кладёт вкэш(далее, если НЕ обновляемся - используем этот эндпоинт)
    @GetMapping("/{id}")
    public ProductDto getProductDto(@PathVariable @Min(0) Long id) {
        return productConverter.entityToDto(productService.findById(id));
    }
    //получает и обновляет кэш
    @GetMapping("/update/{id}")
    public ProductDto updateAndGetProductDto(@PathVariable @Min(0) Long id) {
        return productConverter.entityToDto(productService.updateAndFindById(id));
    }
    //чистка кэша
    @GetMapping("/evict")
    public ResponseEntity<?> evictCache(){
        productService.evictCache();
        return ResponseEntity.ok().build();
    }

    // TODO: 12.06.2023 поддержать на фронте
    //  созранение картинки @RequestParam(value = "product_picture"...)
    //  , пока лошика по картинкам будет здесь для наглядности
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createOrUpdateProduct(@RequestHeader String username,
                                            @RequestBody ProductDto productDto,
                                            @RequestParam(value = "product_picture",
                                                    required = false) MultipartFile multipartFile) throws IOException {
        //если подгружена картинка - назначаем productDto.setPicture_id
        if(multipartFile != null){
           PictureDto pictureDto = PictureDto.builder()
                   .fileName(multipartFile.getOriginalFilename())
                   .contentType(multipartFile.getContentType())
                   .bytes(multipartFile.getBytes())
                   .build();

            if(productDto.getPicture_id() > 1) pictureServiceIntegration.deletePictureById(productDto.getPicture_id());
            Long picId = pictureServiceIntegration.savePictureDtoAndReturnId(pictureDto);
            productDto.setPicture_id(picId);
        }
        return productConverter.entityToDto(productService.saveOrUpdate(productDto, username));
    }

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
}