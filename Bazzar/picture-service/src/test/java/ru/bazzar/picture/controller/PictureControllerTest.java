package ru.bazzar.picture.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.bazzar.picture.api.PictureDto;
import ru.bazzar.picture.api.ResourceNotFoundException;
import ru.bazzar.picture.converters.PictureConverter;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.util.FileResourcesUtils;

import java.io.InputStream;
//НЕ ВЫПОЛНЯТЬ НА УДАЛЕННОЙ БД!!!
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
                .uri("/api/v1/picture/find-pic-dto/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PictureDto.class)
                .returnResult()
                .getResponseBody();

        assert pictureDtoByHttp != null;
        Assertions.assertEquals(pictureDtoByHttp.getId(),1L);
        Assertions.assertEquals(pictureDtoByHttp.getFileName(),"defaultnophotopic.jpg");

    }

    @Test
    void test_savePicDtoAndReturnId() {
        Long pictureIdAfterSaveDtoByHttp =
                webTestClient.post()
                        .uri("/api/v1/picture/save-dto-return-id")
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

    @Test
    void test_findPictureByIdAndResponse() {
        webTestClient.get()
                        .uri("/api/v1/picture/1")
                        .exchange()
                        .expectStatus().isOk();
    }

    @Test
    void test_deletePic() {
        pictureRepository.save(smallPicture);
        Long id = pictureRepository
                .findByFileName(smallPicture.getFileName()).get().getId();

        webTestClient.delete()
               .uri("/api/v1/picture/delete/"+id)
               .exchange()
               .expectStatus().isOk();
    }

    @Test
    void test_savePic() {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part(
                "multipart-pic"
                        ,new ClassPathResource("pic_example/test.jpeg"))
                .contentType(MediaType.MULTIPART_FORM_DATA);

        webTestClient.post()
                .uri("/api/v1/picture/save")
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isCreated();
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