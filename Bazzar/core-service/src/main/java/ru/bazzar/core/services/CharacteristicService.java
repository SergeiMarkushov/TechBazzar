package ru.bazzar.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.converters.CharacteristicConverter;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.repositories.CharacteristicRepository;
import ru.bazzar.core.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CharacteristicConverter characteristicConverter;


    public Characteristic findById(Long id) {
        return characteristicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Характеристика не найдена"));
    }

    public void deleteById(Long id) {
        Optional<Characteristic> characteristicOptional = characteristicRepository.findById(id);

        if (characteristicOptional.isPresent()) {
            Characteristic characteristic = characteristicOptional.get();
            Product product = characteristic.getProduct();
            product.getCharacteristics().remove(characteristic);
            characteristicRepository.delete(characteristic);
            productRepository.save(product);
        } else {
            throw new ResourceNotFoundException("Характеристика не найдена");
        }
    }

    public List<Characteristic> findByProduct(ProductDto productDto) {
        return productService.findById(productDto.getId()).getCharacteristics();
    }

public List<Characteristic> findByProductId(Long productId) {
    return productService.findById(productId).getCharacteristics();
}

    public void saveOrUpdateCharacteristicsInProduct(Long productId, List<CharacteristicDto> characteristicsDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден"));
        List<Characteristic> characteristics = characteristicsDto
                .stream().map(characteristicConverter::dtoToEntity)
                .collect(Collectors.toList());
        product.setCharacteristics(characteristics);

        for (Characteristic characteristic : characteristics) {
            characteristic.setProduct(product);
        }
        characteristicRepository.saveAll(characteristics);
        productRepository.save(product);
    }

    public void saveOrUpdateCharacteristic(Long id, CharacteristicDto characteristicDto) {
        Characteristic existingCharacteristic = characteristicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Характеристика не найдена"));
        existingCharacteristic.setName(characteristicDto.getName());
        characteristicRepository.save(existingCharacteristic);
    }

    public List<Product> getProductByCharacteristicName(String characteristicName) {
        return characteristicRepository.findProductsByCharacteristicName(characteristicName);
    }

    public Long getCharacteristicIdByName(String name) {
        Characteristic characteristic = characteristicRepository.findByName(name);
        return characteristic != null ? characteristic.getId() : null;
    }

    public List<Characteristic> getCharacteristicsByProductAndName(ProductDto productDto, String name) {
        List<Characteristic> characteristics = characteristicRepository.findByProductId(productDto.getId());
        List<Characteristic> filteredCharacteristics = new ArrayList<>();
        for (Characteristic characteristic : characteristics) {
            if (characteristic.getName().equalsIgnoreCase(name)) {
                filteredCharacteristics.add(characteristic);
            }
        }
        return filteredCharacteristics;
    }



    public List<Product> findProductsByCharacteristicName(String characteristicName) {
        return characteristicRepository.findProductsByCharacteristicName(characteristicName);
    }

    public void deleteByProductId(Long productId) {
        characteristicRepository.deleteByProductId(productId);
    }
}
