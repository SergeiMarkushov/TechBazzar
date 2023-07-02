package ru.bazzar.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bazzar.core.entities.PurchaseHistory;

import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    List<PurchaseHistory> findAllByEmailIgnoreCase(String email);
    @Query("SELECT SUM(p.quantity) FROM PurchaseHistory p WHERE p.email = ?1")
    Integer countAllQuantityByEmailIgnoreCase(String email);
}
