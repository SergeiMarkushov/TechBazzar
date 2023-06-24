package ru.bazzar.picture.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.bazzar.picture.api.PictureDto;
import ru.bazzar.picture.api.ResourceNotFoundException;
import ru.bazzar.picture.converters.PictureConverter;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.util.FileResourcesUtils;

import java.io.InputStream;
//НЕ ВЫПОЛНЯТЬ НА УДАЛЕННОЙ БД!!! ----переделать!
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
class PictureControllerTest {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PictureConverter pictureConverter;

    Picture smallPicture;

    @BeforeEach
    void setUp() {
        initPicture();
    }

    @Test
    void test_findPictureByIdAndReturnDto() {
        PictureDto pictureDtoByHttp =
                webTestClient.get()
                .uri("/api/v1/picture/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PictureDto.class)
                .returnResult()
                .getResponseBody();

        assert pictureDtoByHttp != null;
        Assertions.assertEquals(pictureDtoByHttp.getFileName(),"defaultnophotopic.jpg");
        Assertions.assertEquals(pictureDtoByHttp.getId(),1L);
    }

    @Test
    void test_savePicDtoAndReturnId() {
        Long pictureIdAfterSaveDtoByHttp =
                webTestClient.post()
                        .uri("/api/v1/picture")
                        .body(BodyInserters.fromValue(pictureConverter.entityToDto(smallPicture)))
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(Long.class)
                        .returnResult()
                        .getResponseBody();
        assert pictureIdAfterSaveDtoByHttp != null;
        Picture pictureDB = pictureRepository.
                findById(pictureIdAfterSaveDtoByHttp).
                orElseThrow(()-> new ResourceNotFoundException("Картинка не найдена!"));
        Assertions.assertEquals(pictureDB.getId(),pictureIdAfterSaveDtoByHttp);
    }

    private void initPicture() {
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        InputStream is = fileResourcesUtils.getFileFromResourceAsStream("pic_example/test.jpeg");
        //до 1Mb
        smallPicture = Picture.builder()
                .contentType("image/jpeg")
                .fileName("test.jpeg")
                .bytes(fileResourcesUtils.convertStreamToByteArr(is))
                .build();
    }

}
