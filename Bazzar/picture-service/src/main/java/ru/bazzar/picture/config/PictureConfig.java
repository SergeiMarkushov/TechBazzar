package ru.bazzar.picture.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.service.PictureService;
import ru.bazzar.picture.util.FileResourcesUtils;

import javax.annotation.PostConstruct;
import java.io.*;


@Configuration
@RequiredArgsConstructor
public class PictureConfig {
    private final PictureService pictureService;
    private final PictureRepository pictureRepository;

    //создание картинки по умолчанию с id 1L
    @PostConstruct
    public void saveDefaultNoPhotoPic(){
        String fileName = "pic_example/defaultnophotopic.jpg";
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        InputStream is = fileResourcesUtils.getFileFromResourceAsStream(fileName);
        Picture defPic = Picture.builder()
                .fileName("defaultnophotopic.jpg")
                .contentType("image/jpeg")
                .bytes(fileResourcesUtils.convertStreamToByteArr(is))
                .build();
        if(pictureRepository.findById(1L).orElse(null) == null) pictureService.save(defPic);
    }
}
