package ru.bazzar.picture.converters;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.picture.api.PictureDto;
import ru.bazzar.picture.entities.Picture;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PictureConverter {
    private final ModelMapper modelMapper;

    public Picture multipartToEntity(MultipartFile file) throws IOException {
        return Picture.builder()
                .fileName(file.getOriginalFilename())
                .contentType("image/jpeg")
                .bytes(file.getBytes())
                .build();
    }

    public PictureDto entityToDto(Picture picture) {
        return modelMapper.map(picture, PictureDto.class);
//        return PictureDto.builder()
//                .id(picture.getId())
//                .fileName(picture.getFileName())
//                .contentType(picture.getContentType())
//                .bytes(picture.getBytes())
//                .build();
    }

    public Picture dtoToEntity(PictureDto pictureDto) {
        return modelMapper.map(pictureDto, Picture.class);
//        return Picture.builder()
//                .id(pictureDto.getId())
//                .fileName(pictureDto.getFileName())
//                .contentType(pictureDto.getContentType())
//                .bytes(pictureDto.getBytes())
//                .build();
    }
}
