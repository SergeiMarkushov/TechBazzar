package ru.bazzar.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.entities.Product;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacteristicConverter {
    public Characteristic dtoToEntity(CharacteristicDto characteristicDto) {
        return new Characteristic(
                characteristicDto.getId(),
                characteristicDto.getName(),
                productDtoToEntity(characteristicDto.getProductDto())
        );
    }

    public CharacteristicDto entityToDto(Characteristic characteristic) {
        return new CharacteristicDto(
                characteristic.getId(),
                characteristic.getName(),
                productEntityToDto(characteristic.getProduct())
        );
    }

    public ProductDto productEntityToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getOrganizationTitle(),
                product.getPrice(),
                product.getQuantity(),
                product.isConfirmed(),
                product.getCharacteristics()
                        .stream().map(this::entityToDto)
                        .collect(Collectors.toList())
        );
    }

    public Product productDtoToEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .isConfirmed(productDto.isConfirmed())
                .characteristics(productDto.getCharacteristicsDto()
                        .stream().map(this::dtoToEntity).collect(Collectors.toList()))
                .build();
    }
}
