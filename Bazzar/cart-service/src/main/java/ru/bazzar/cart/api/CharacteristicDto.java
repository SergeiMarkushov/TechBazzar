package ru.bazzar.cart.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicDto {
    private Long id;
    private String name;
    private ProductDto productDto;
}
