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

    @GetMapping("/{id}") //  "/find-pic-dto"
    public PictureDto findPictureByIdAndReturnDto(@PathVariable Long id){
        return pictureConverter.entityToDto(pictureService.findById(id));
    }

    @PostMapping()//  "/save-dto-return-id"
    public Long savePicDtoAndReturnId(@RequestBody PictureDto pictureDto){
        Picture p = pictureService.save(pictureConverter.dtoToEntity(pictureDto));
        return p.getId();
    }

    @DeleteMapping("/{id}")//  "/delete/{id}"
    public void deletePic(@PathVariable Long id){
        pictureService.deleteById(id);
    }


    @GetMapping("/evict")
    public void evict(){
        pictureService.cacheEvict();
    }
}

//Не используемые методы

//    @PostMapping("/save")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void savePic(@RequestParam(value = "multipart-pic") MultipartFile multipartFile) {
//        Picture picture = null;
//        try {
//            picture = Picture.builder()
//                    .fileName(multipartFile.getOriginalFilename())
//                    .contentType(multipartFile.getContentType())
//                    .bytes(multipartFile.getBytes())
//                    .build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        pictureService.save(picture);
//    }

//    @GetMapping("/entity/{id}")
//    public  ResponseEntity<?> findPictureByIdAndResponse(@PathVariable Long id){
//        PictureDto pictureDto = pictureConverter.entityToDto(pictureService.findById(id));
//
//        return ResponseEntity.ok()
//                .header("pic_id", pictureDto.getId().toString())
//                .header("filename", pictureDto.getFileName())
//                .contentType(MediaType.valueOf(pictureDto.getContentType()))
//                .body(new InputStreamResource(new ByteArrayInputStream(pictureDto.getBytes())));
//    }