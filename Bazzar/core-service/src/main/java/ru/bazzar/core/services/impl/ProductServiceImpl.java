package ru.bazzar.core.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.api.*;
import ru.bazzar.core.configs.GlobalEnum;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.integrations.OrganizationServiceIntegration;
import ru.bazzar.core.integrations.UserServiceIntegration;
import ru.bazzar.core.repositories.ProductRepository;
import ru.bazzar.core.repositories.specifications.ProductSpecifications;
import ru.bazzar.core.services.interf.ProductService;
import ru.bazzar.core.utils.MyQueue;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends AbstractService<Product, Long> implements ProductService {
    private final ProductRepository productRepository;
    private final OrganizationServiceIntegration organizationService;
    private final UserServiceIntegration userService;
    private MyQueue<Product> productQueue = new MyQueue<>();
    private String adminEmail = GlobalEnum.ADMIN_EMAIL.getValue();
    @Override
    JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @CacheEvict("productCache")//чистка кэша при удалении(синхронизация БД и кэша)
    public void deleteById(Long id){
        getRepository().deleteById(id);
    }

    @Override
    @Cacheable(cacheNames="productCache", sync=true, key="#id")
    public Product findById(Long id) {
        return getRepository().findById(id)
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

    // TODO: 28.05.2023 подумать, может лучше валидацию сделать вместо ифов на ДТОшках
    public Product saveDto(ProductDto productDto, String username) {
        if (productDto.getId() != null) {
            Product productFromBd = getRepository().findById(productDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + productDto.getId()));
            if (productDto.getTitle() != null) {
                productFromBd.setTitle(productDto.getTitle());
            }
            if (productDto.getDescription() != null) {
                productFromBd.setDescription(productDto.getDescription());
            }
            if (productDto.getPrice() != null) {
                productFromBd.setPrice(productDto.getPrice());
            }
            // TODO: 28.05.2023 а разве не >0  - ?
            if (productDto.getQuantity() != 0) {
                productFromBd.setQuantity(productFromBd.getQuantity() + productDto.getQuantity());
            }
            return productRepository.save(productFromBd);
        } else {
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
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
            product.setOrganizationTitle(organizationDto.getTitle());
            product.setPrice(productDto.getPrice());
            product.setConfirmed(false);
            product.setQuantity(productDto.getQuantity());
            //метод из интерфейса!
            save(product);
            return product;

        }
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
        Product productFromDB = getRepository().findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + product.getId()));
        if (productFromDB.getQuantity() >= product.getQuantity()) {
            productFromDB.setQuantity(productFromDB.getQuantity() - product.getQuantity());
            productRepository.save(productFromDB);
        } else {
            throw new AccessException("Недостаточное количество продукта, id: " + product.getId());
        }
    }

}
