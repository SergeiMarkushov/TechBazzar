package ru.bazzar.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.entities.Characteristic;
@Service
@RequiredArgsConstructor
public class CharacteristicConverter {
    private final ProductConverter productConverter;
    public Characteristic dtoToEntity(CharacteristicDto characteristicDto) {
        return new Characteristic(
                characteristicDto.getId(),
                characteristicDto.getName(),
                productConverter.dtoToEntity(characteristicDto.getProductDto())
        );
    }

    public CharacteristicDto entityToDto(Characteristic characteristic) {
        return new CharacteristicDto(
                characteristic.getId(),
                characteristic.getName(),
                productConverter.entityToDto(characteristic.getProduct())
        );
    }
}
