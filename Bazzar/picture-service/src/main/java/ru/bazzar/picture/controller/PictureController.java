package ru.bazzar.picture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.picture.api.PictureDto;
import ru.bazzar.picture.converters.PictureConverter;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.service.PictureService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/picture")
public class PictureController {

    private final PictureService pictureService;
    private final PictureConverter pictureConverter;

    @GetMapping("/{id}")
    public PictureDto findPictureByIdAndReturnDto(@PathVariable Long id){
        return pictureConverter.entityToDto(pictureService.findById(id));
    }

    @PostMapping()
    public Long savePicDtoAndReturnId(@RequestBody PictureDto pictureDto){
        Picture p = pictureService.save(pictureConverter.dtoToEntity(pictureDto));
        return p.getId();
    }

    @DeleteMapping("/{id}")
    public void deletePic(@PathVariable Long id){
        pictureService.deleteById(id);
    }


    @GetMapping("/evict")
    public void evict(){
        pictureService.cacheEvict();
    }
}
