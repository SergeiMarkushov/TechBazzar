package ru.bazzar.core.converters;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.entities.Characteristic;

@Component
@RequiredArgsConstructor
public class CharacteristicConverter {
    private final ModelMapper modelMapper;
    public CharacteristicDto entityToDto(Characteristic characteristic) {
        return modelMapper.map(characteristic, CharacteristicDto.class);
    }

    public Characteristic dtoToEntity(CharacteristicDto characteristicDto) {
        return modelMapper.map(characteristicDto, Characteristic.class);
    }
}
