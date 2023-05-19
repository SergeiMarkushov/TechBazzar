package ru.bazzar.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bazzar.auth.entities.UsersProductsList;

import java.util.Optional;

@Repository
public interface UsersProductsRepository extends JpaRepository<UsersProductsList, Long> {
    Optional<UsersProductsList> findByUserIdAndProductId(Long userId, Long productId);
}
