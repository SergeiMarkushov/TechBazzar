package ru.bazzar.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.entities.Product;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductConverter {
    private final CharacteristicConverter characteristicConverter;

    public ProductDto entityToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setOrganizationTitle(product.getOrganizationTitle());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setConfirmed(product.isConfirmed());
        dto.setCharacteristicsDto(product.getCharacteristics()
                .stream().map(characteristicConverter::entityToDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public Product dtoToEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .isConfirmed(productDto.isConfirmed())
                .characteristics(productDto.getCharacteristicsDto()
                        .stream().map(characteristicConverter::dtoToEntity).collect(Collectors.toList()))
                .build();
    }
}
