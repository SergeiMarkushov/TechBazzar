package ru.bazzar.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bazzar.core.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select count(r) from Review r where r.product.id = ?1")
    Integer countByProductId(Long productId);
}
