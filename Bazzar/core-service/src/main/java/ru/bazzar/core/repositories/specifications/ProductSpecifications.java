package ru.bazzar.core.repositories.specifications;


import org.springframework.data.jpa.domain.Specification;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.entities.Product;

import javax.persistence.criteria.Join;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(Integer minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceLessOrEqualsThan(Integer maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), String.format("%%%s%%", titlePart.toUpperCase()));
    }

    public static Specification<Product> titleCompanyLike(String organizationTitle) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            String formattedOrganizationTitle = "%" + organizationTitle + "%";
            return criteriaBuilder.like(criteriaBuilder.upper(root.get("organizationTitle")), formattedOrganizationTitle.toUpperCase());
        };
    }

    public static Specification<Product> characteristicLike(String characteristicPart) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Characteristic> characteristicJoin = root.join("characteristics");
            return criteriaBuilder.like(
                    criteriaBuilder.lower(characteristicJoin.get("name")),
                    String.format("%%%s%%", characteristicPart.toLowerCase())
            );
        };
    }
}
