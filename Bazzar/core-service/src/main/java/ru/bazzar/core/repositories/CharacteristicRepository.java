package ru.bazzar.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.entities.Product;

import java.util.List;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    List<Characteristic> findByProductId(Long productId);
    @Query("SELECT p FROM Product p JOIN Characteristic c ON p.id = c.product.id WHERE c.name = :characteristicName")
    List<Product> findProductsByCharacteristicName(@Param("characteristicName") String characteristicName);

}
