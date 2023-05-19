package ru.bazzar.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.core.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
