package ru.bazzar.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.core.entities.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
