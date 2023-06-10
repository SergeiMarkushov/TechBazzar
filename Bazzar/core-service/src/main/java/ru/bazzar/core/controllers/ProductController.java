package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.*;
import ru.bazzar.core.converters.ProductConverter;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.integrations.OrganizationServiceIntegration;
import ru.bazzar.core.services.CharacteristicService;
import ru.bazzar.core.services.ProductService;
import ru.bazzar.core.utils.MyQueue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    private final CharacteristicService characteristicService;
    private MyQueue<Product> productQueue = new MyQueue<>();//?

//********************************************
    @GetMapping("/test")
    public void testProduct(){
        System.out.println("Это тест!!!!!!");
    }

    @PostMapping("/valid_test")
    public TestDTO valid(@RequestBody TestDTO testDTO) {
        testDTO.setStatus(true);
        System.out.println(testDTO.toString());
        return testDTO;
        /* -> для ручного тестирования
        {
            "title": "some title",
                "number": "6",
                "bigDecimalPrice": "1111111.33",
                "email": "aaaa@a.d",
                "status": "false"
        }
        */
    }
//********************************************
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

    @GetMapping("/{id}")
    public ProductDto getProductDto(@PathVariable @Min(0) Long id) {
        return productConverter.entityToDto(productService.findById(id));
    }

    @PostMapping//AOP
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createOrUpdateProduct(@RequestHeader String username, @RequestBody ProductDto productDto) {
        return productConverter.entityToDto(productService.saveOrUpdate(productDto, username));
    }

    @GetMapping("/not_confirmed")
    public ProductDto notConfirmed() throws ResourceNotFoundException {
        return productConverter.entityToDto(productService.notConfirmed());
    }

    @GetMapping("/confirm/{title}")//AOP
    public void confirm(@PathVariable @NotBlank String title) {
        productService.confirm(title);
    }

    @DeleteMapping("/{id}")//AOP
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
