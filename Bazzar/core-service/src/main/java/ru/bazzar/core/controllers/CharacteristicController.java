package ru.bazzar.core.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.converters.CharacteristicConverter;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.services.CharacteristicService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/characteristics")
public class CharacteristicController {
    private final CharacteristicService characteristicService;
    private final CharacteristicConverter characteristicConverter;
    private final ModelMapper modelMapper;

    @PostMapping("/{productId}/characteristics")
    public ResponseEntity<?> addCharacteristicsToProduct(@PathVariable Long productId, @RequestBody List<CharacteristicDto> characteristicDtos) {
        characteristicService.saveOrUpdateCharacteristicByProductId(productId, characteristicDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{characteristicId}")
    public ResponseEntity<?> updateCharacteristic(@PathVariable @Min(0) Long characteristicId, @RequestBody CharacteristicDto characteristicDto) {
        characteristicService.saveOrUpdateCharacteristic(characteristicId, characteristicDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-by-productId/{productId}")
    public List<CharacteristicDto> showProductCharacteristics(@PathVariable @Min(0) Long productId) {
        List<Characteristic> characteristics = characteristicService.findByProductId(productId);

        return  characteristics.stream()
                .map(characteristicConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/find-products-by-characteristicName/{characteristicName}")
    public List<ProductDto> showProductsByCharacteristicName(@PathVariable String characteristicName) {
        return characteristicService.findProductsByCharacteristicName(characteristicName)
                .stream().map(products -> modelMapper.map(products, ProductDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{characteristicId}")
    public void deleteCharacteristics(@PathVariable @Min(0) Long characteristicId) {
        characteristicService.deleteCharacteristic(characteristicId);
    }
}
