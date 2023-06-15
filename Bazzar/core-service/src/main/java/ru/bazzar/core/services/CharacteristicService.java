package ru.bazzar.core.services;

import io.netty.handler.codec.http2.Http2Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.repositories.CharacteristicRepository;
import ru.bazzar.core.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacteristicService {
    private final CharacteristicRepository characteristicRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public Characteristic findById(Long id) {
        return characteristicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Характеристика не найдена"));
    }
    public void deleteCharacteristic(Long characteristicId) {
        Characteristic characteristic = characteristicRepository.findById(characteristicId)
                .orElseThrow(() -> new ResourceNotFoundException("Характеристика не найдена"));

        characteristicRepository.delete(characteristic);
    }

    public List<Characteristic> findByProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return characteristicRepository.findByProductId(product.getId());
    }

    public List<Characteristic> findByProductId(Long id) {
        return characteristicRepository.findByProductId(id);
    }

    public void saveOrUpdateCharacteristicByProduct(ProductDto productDto, List<CharacteristicDto> characteristicDtos) {
        Product product = modelMapper.map(productDto, Product.class);

        List<Characteristic> existingCharacteristics = characteristicRepository.findByProductId(product.getId());

        characteristicRepository.deleteAll(existingCharacteristics);

        List<Characteristic> newCharacteristics = characteristicDtos.stream()
                .map(characteristicDto -> modelMapper.map(characteristicDto, Characteristic.class))
                .collect(Collectors.toList());

        newCharacteristics.forEach(characteristic -> characteristic.setProduct(product));

        characteristicRepository.saveAll(newCharacteristics);
    }
    public void saveOrUpdateCharacteristicByProductId(Long productId, List<CharacteristicDto> characteristicDtos) {
        List<Characteristic> existingCharacteristics = characteristicRepository.findByProductId(productId);

        characteristicRepository.deleteAll(existingCharacteristics);
        
        List<Characteristic> newCharacteristics = characteristicDtos.stream()
                .map(characteristicDto -> {
                    Characteristic characteristic = modelMapper.map(characteristicDto, Characteristic.class);
                    characteristic.setProduct(productRepository.findById(productId)
                            .orElseThrow(()-> new ResourceNotFoundException("Продукт не найден")));
                    return characteristic;
                })
                .collect(Collectors.toList());

        characteristicRepository.saveAll(newCharacteristics);
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

    public void saveOrUpdateCharacteristic(Long characteristicId, CharacteristicDto characteristicDto) {
        Characteristic existingCharacteristic = characteristicRepository.findById(characteristicId)
                .orElseThrow(() -> new ResourceNotFoundException("Характеристика не найдена"));

        existingCharacteristic.setName(characteristicDto.getName());

        characteristicRepository.save(existingCharacteristic);
        Characteristic characteristic = characteristicRepository.findById(characteristicId)
                .orElseThrow(() -> new ResourceNotFoundException("Характеристика не найдена"));

        characteristic.setName(characteristicDto.getName());

        characteristicRepository.save(characteristic);
    }

    public List<Product> findProductsByCharacteristicName(String characteristicName) {
        return characteristicRepository.findProductsByCharacteristicName(characteristicName);
    }
}