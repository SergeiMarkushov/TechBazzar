package ru.bazzar.core.servises.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.api.UserDto;
import ru.bazzar.core.api.AccessException;
import ru.bazzar.core.entities.Organization;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.integrations.UserServiceIntegration;
import ru.bazzar.core.repositories.ProductRepository;
import ru.bazzar.core.repositories.specifications.ProductSpecifications;
import ru.bazzar.core.servises.OrganizationService;
import ru.bazzar.core.servises.interf.ProductService;
import ru.bazzar.core.utils.IdentityMap;
import ru.bazzar.core.utils.MyQueue;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends AbstractService<Product, Long> implements ProductService {
    private final ProductRepository productRepository;
    private final OrganizationService organizationService;
    private final UserServiceIntegration userService;
    private IdentityMap identityMap = new IdentityMap();
    private MyQueue<Product> productQueue = new MyQueue<>();

    @Override
    JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Override
    public Product findById(Long id) {
        Product product = this.identityMap.getProductMap(id);
        if (product != null) {
            log.info("Product found in the Map");
            return product;
        } else {
            // Try to find product in the database
            product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
            if (product != null) {
                this.identityMap.addProductMap(product);
                log.info("Product found in DB.");
                return product;
            }
        }
        return null;
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
//            if (productDto.getOrganizationTitle() != null) {
//                Organization organization = organizationService.findByTitleIgnoreCase(productDto.getOrganizationTitle());
//                productFromBd.setOrganization(organization);
//            }
            if (productDto.getPrice() != null) {
                productFromBd.setPrice(productDto.getPrice());
            }
            if (productDto.getQuantity() != 0) {
                productFromBd.setQuantity(productFromBd.getQuantity() + productDto.getQuantity());
            }
            return productRepository.save(productFromBd);
        } else {
            Organization organization = organizationService.findByTitleIgnoreCase(productDto.getOrganizationTitle());
            if (organization == null) {
                throw new AccessException("Организация не прошла модерацию, попробуйте добавить продукт позже.");
            }
            if (!username.equalsIgnoreCase(organization.getOwner())) {
                throw new AccessException("Только владелец компании может добавлять товары в магазин.");
            }
            UserDto userDto = userService.getUser(organization.getOwner());
            if (!userDto.isActive()) {
                throw new AccessException("Владелец организации забанен, обратитесь к администратору n.v.bekhter@mail.ru");
            }

            Product product = new Product();
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
//            product.setOrganization(organization);
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
        Product product = productRepository.findByTitleIgnoreCase(title).get();
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
}
