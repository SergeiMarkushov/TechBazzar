package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bazzar.core.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicDto {
    private Long id;
    private String name;
    private ProductDto productDto;
}
