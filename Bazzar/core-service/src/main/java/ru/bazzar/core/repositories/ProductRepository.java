package ru.bazzar.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.bazzar.core.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByIsConfirmed(boolean isConfirmed);
    Optional<Product> findByTitleIgnoreCase(String title);

}
