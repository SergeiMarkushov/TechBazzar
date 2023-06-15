package ru.bazzar.core.converters;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.bazzar.core.api.CharacteristicDto;
import ru.bazzar.core.api.ProductDto;
import ru.bazzar.core.entities.Characteristic;
import ru.bazzar.core.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductConverter {
    private final ModelMapper modelMapper;

    public ProductDto entityToDto(Product product) {
        ProductDto productDTO = modelMapper.map(product, ProductDto.class);
        List<CharacteristicDto> characteristicDTOs = new ArrayList<>();

        if (product.getCharacteristics() == null) {
            return productDTO;
        }

        for (Characteristic characteristic : product.getCharacteristics()) {
            CharacteristicDto characteristicDTO = modelMapper.map(characteristic, CharacteristicDto.class);
            characteristicDTOs.add(characteristicDTO);
        }

        productDTO.setCharacteristicsDto(characteristicDTOs);
        productDTO.setPictureId(product.getPictureId());
        return productDTO;
    }

    public Product dtoToEntity(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        List<Characteristic> characteristics = new ArrayList<>();

        for (Characteristic characteristicFromProduct : product.getCharacteristics()) {
            Characteristic characteristic = modelMapper.map(characteristicFromProduct, Characteristic.class);
        }
        product.setCharacteristics(characteristics);
        product.setPictureId(productDto.getPictureId());
        return product;
    }

//    public ProductDto entityToDto(Product product) {
//        ProductDto dto = new ProductDto();
//        dto.setId(product.getId());
//        dto.setTitle(product.getTitle());
//        dto.setDescription(product.getDescription());
//        dto.setOrganizationTitle(product.getOrganizationTitle());
//        dto.setPrice(product.getPrice());
//        dto.setQuantity(product.getQuantity());
//        dto.setConfirmed(product.isConfirmed());
////        dto.setCharacteristicsDto();
//        return dto;
//    }
//
//    public Product dtoToEntity(ProductDto productDto) {
//        return Product.builder()
//                .id(productDto.getId())
//                .title(productDto.getTitle())
//                .description(productDto.getDescription())
//                .price(productDto.getPrice())
//                .quantity(productDto.getQuantity())
//                .isConfirmed(productDto.isConfirmed())
////                .characteristics();
//                .build();
//    }
}
