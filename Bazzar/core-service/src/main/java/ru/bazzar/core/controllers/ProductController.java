package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.api.PageDto;
import ru.bazzar.api.ProductDto;
import ru.bazzar.api.ResourceNotFoundException;
import ru.bazzar.core.converters.ProductConverter;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.servises.OrganizationService;
import ru.bazzar.core.servises.impl.ProductServiceImpl;
import ru.bazzar.core.servises.interf.SimpleService;
import ru.bazzar.core.utils.MyQueue;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController extends AbstractRestController<Product, Long> {
    private final ProductServiceImpl productServiceImpl;
    private final ProductConverter productConverter;
    private final OrganizationService organizationService;
    private MyQueue<Product> productQueue = new MyQueue<>();

    @GetMapping
    public PageDto<ProductDto> getProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
//            @RequestParam(name = "keyword_part", required = false) String keywordPart,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }

        Page<ProductDto> jpaPage = productServiceImpl.find(minPrice, maxPrice, titlePart, page).map(
                productConverter::entityToDto
        );
        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(jpaPage.getNumber());
        out.setTotalPages(jpaPage.getTotalPages());
        List<ProductDto> productDtos = new ArrayList<>();
        for (ProductDto productDto : jpaPage.getContent()) {
            if (organizationService.isOrgBun(productDto.getOrganizationTitle())) {
                if (productDto.isConfirmed()) {
                    productDtos.add(productDto);
                }
            }
        }
        out.setItems(productDtos);
        return out;
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productConverter.entityToDto(getService().findById(id));
        //return productConverter.entityToDto(productServiceImpl.findById(id));
        //getService().ПОКАЗАТЬ!
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createOrUpdateProduct(@RequestHeader String username, @RequestBody ProductDto productDto) throws ResourceNotFoundException {
        return productConverter.entityToDto(productServiceImpl.saveDto(productDto, username));
    }

    @GetMapping("/not_confirmed")
    public ProductDto notConfirmed() throws ResourceNotFoundException {
        return productConverter.entityToDto(productServiceImpl.notConfirmed());
    }

    @GetMapping("/confirm/{title}")
    public void confirm(@PathVariable String title) {
        productServiceImpl.confirm(title);
    }

    @Override
    SimpleService<Product, Long> getService() {
        return productServiceImpl;
    }

}
