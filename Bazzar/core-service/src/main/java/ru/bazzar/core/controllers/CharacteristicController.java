package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.converters.CharacteristicConverter;
import ru.bazzar.core.converters.ProductConverter;
import ru.bazzar.core.services.CharacteristicService;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/characteristics")
public class CharacteristicController {
    private final CharacteristicService characteristicService;
    private final CharacteristicConverter characteristicConverter;
    private final ProductConverter productConverter;

    @PostMapping("/{productId}")
    public void addCharacteristicsToProduct(@PathVariable @Min(0) Long productId, @RequestBody List<CharacteristicDto> characteristicsDto) {
        characteristicService.saveOrUpdateCharacteristicsInProduct(productId, characteristicsDto);
    }
    @PutMapping("/{characteristicId}")
    public void updateCharacteristic(@PathVariable @Min(0) Long characteristicId, @RequestBody CharacteristicDto characteristicDto) {
        characteristicService.saveOrUpdateCharacteristic(characteristicId, characteristicDto);
    }

    @GetMapping("/find-by-productId/{productId}")
    public List<CharacteristicDto> showProductCharacteristics(@PathVariable @Min(0) Long productId) {
        return characteristicService.findByProductId(productId).stream().map(characteristicConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/find-products-by-characteristicName/{characteristicName}")
    public List<ProductDto> showProductsByCharacteristicName(@PathVariable String characteristicName) {
        return characteristicService.findProductsByCharacteristicName(characteristicName).stream().map(productConverter::entityToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{characteristicId}")
    public void deleteCharacteristics(@PathVariable @Min(0) Long characteristicId) {
        characteristicService.deleteById(characteristicId);
    }
}
