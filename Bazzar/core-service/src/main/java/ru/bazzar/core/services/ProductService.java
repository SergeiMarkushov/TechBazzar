package ru.bazzar.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.api.AccessException;
import ru.bazzar.core.api.OrganizationDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.configs.GlobalEnum;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.integrations.OrganizationServiceIntegration;
import ru.bazzar.core.integrations.UserServiceIntegration;
import ru.bazzar.core.repositories.ProductRepository;
import ru.bazzar.core.repositories.specifications.ProductSpecifications;
import ru.bazzar.core.utils.MyQueue;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final OrganizationServiceIntegration organizationService;
    private final UserServiceIntegration userService;
    private MyQueue<Product> productQueue = new MyQueue<>();
    private final String adminEmail = GlobalEnum.ADMIN_EMAIL.getValue();

    @CacheEvict(value = "productCache", key = "#id")
    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    @CacheEvict(value = "productCache", allEntries = true)
    public void evictCache() {}

    @Cacheable(value = "productCache", key = "#id")
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
    }

    @CachePut(value = "productCache", key = "#id")
    public Product updateAndFindById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
    }

    public Page<Product> find(Integer minPrice, Integer maxPrice, String titlePart, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessOrEqualsThan(maxPrice));
        }
        if (titlePart != null) {
            spec = spec.and(ProductSpecifications.titleLike(titlePart));
        }
//        if (keywordPart != null) {
//            spec = spec.and(ProductSpecifications.keywordLike(keywordPart));
//        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, 5));
    }

    public Product notConfirmed(){
        if (productQueue.isEmpty()) {
            List<Product> notConfirmList = productRepository.findAllByIsConfirmed(false);
            if (notConfirmList.isEmpty()) {
                throw new AccessException("Не подтвержденных продуктов больше нет.");
            }
            for (Product product : notConfirmList) {
                productQueue.enqueue(product);
            }
        }
        return productQueue.dequeue();
    }

    public void confirm(String title){
        Product product = productRepository.findByTitleIgnoreCase(title)
                .orElseThrow(()-> new ResourceNotFoundException("Продукт: " + title + " не найден!"));
        product.setConfirmed(true);
        productRepository.save(product);
    }

    public void changeQuantity(Product product){
        Product productFromDB = productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + product.getId()));
        if (productFromDB.getQuantity() >= product.getQuantity()) {
            productFromDB.setQuantity(productFromDB.getQuantity() - product.getQuantity());
            productRepository.save(productFromDB);
        } else {
            throw new AccessException("Недостаточное количество продукта, id: " + product.getId());
        }
    }

    public Product saveOrUpdate(ProductDto productDto, String username) {
        if(productDto.getId() != null) {
            return updateProduct(productDto.getId(), productDto);
        }else {
            return saveProduct(productDto, username);
        }
    }

    private Product saveProduct(ProductDto productDto, String username) {
        //save
        OrganizationDto organizationDto = organizationService.getOrganizationByTitle(productDto.getOrganizationTitle());
        if (!organizationDto.isActive()) {
            throw new AccessException("Организация не прошла модерацию, попробуйте добавить продукт позже.");
        }
        if (!username.equalsIgnoreCase(organizationDto.getOwner())) {
            throw new AccessException("Только владелец компании может добавлять товары в магазин.");
        }
        if (!userService.getUser(organizationDto.getOwner()).isActive()) {
            throw new AccessException("Владелец организации забанен, обратитесь к администратору: " + adminEmail);
        }

        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setOrganizationTitle(organizationDto.getTitle());//OrganizationServiceIntegration
        product.setPrice(productDto.getPrice());
        product.setConfirmed(false);
        product.setQuantity(productDto.getQuantity());
        //fixme: тут я хз

        //валидируем и возвращаем
        return productRepository.save(product);
    }
    private Product updateProduct(Long id, ProductDto productDto) {
        //update
        Product productFromBd = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));

        if (productDto.getTitle() != null) {
            productFromBd.setTitle(productDto.getTitle());
        }
        if (productDto.getDescription() != null) {
            productFromBd.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != null) {
            productFromBd.setPrice(productDto.getPrice());
        }
        if (productDto.getQuantity() != 0) {
            productFromBd.setQuantity(productFromBd.getQuantity() + productDto.getQuantity());
        }

        //валидируем и возвращаем
        return productRepository.save(productFromBd);
    }
}