package ru.bazzar.core.converters;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.entities.Product;

@Component
@RequiredArgsConstructor
public class CharacteristicConverter {
    private final ModelMapper modelMapper;
    public CharacteristicDto entityToDto(Characteristic characteristic) {
        CharacteristicDto characteristicDto = modelMapper.map(characteristic, CharacteristicDto.class);
        ProductDto productDto = modelMapper.map(characteristic.getProduct(), ProductDto.class);
        characteristicDto.setProductDto(productDto);
        return characteristicDto;
    }

    public Characteristic dtoToEntity(CharacteristicDto characteristicDto) {
        Characteristic characteristic = modelMapper.map(characteristicDto, Characteristic.class);
        Product product = modelMapper.map(characteristicDto.getProductDto(), Product.class);
        characteristic.setProduct(product);
        return characteristic;
    }
}
