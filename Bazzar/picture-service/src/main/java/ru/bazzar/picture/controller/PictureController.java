package ru.bazzar.picture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.picture.api.PictureDto;
import ru.bazzar.picture.converters.PictureConverter;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.service.PictureService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/picture")
public class PictureController {

    private final PictureService pictureService;
    private final PictureConverter pictureConverter;

    @GetMapping("/find-pic-dto/{id}")
    public PictureDto findPictureByIdAndReturnDto(@PathVariable Long id){
        return pictureConverter.entityToDto(pictureService.findById(id));
    }

    @PostMapping("/save-dto-return-id")
    public Long savePicDtoAndReturnId(@RequestBody PictureDto pictureDto){
        Picture p = pictureService.save(pictureConverter.dtoToEntity(pictureDto));
        return p.getId();
    }
    //можно использовать на фронте для загрузки ResponseEntity<?> картинки
    @GetMapping("/{id}")
    public  ResponseEntity<?> findPictureByIdAndResponse(@PathVariable Long id){
        PictureDto pictureDto = pictureConverter.entityToDto(pictureService.findById(id));

        return ResponseEntity.ok()
                .header("pic_id", pictureDto.getId().toString())
                .header("filename", pictureDto.getFileName())
                .contentType(MediaType.valueOf(pictureDto.getContentType()))
                .body(new InputStreamResource(new ByteArrayInputStream(pictureDto.getBytes())));
    }

    @DeleteMapping("/delete/{id}")
    public void deletePic(@PathVariable Long id){
        pictureService.deleteById(id);
    }

}
