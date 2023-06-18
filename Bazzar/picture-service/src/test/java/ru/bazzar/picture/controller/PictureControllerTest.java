package ru.bazzar.picture.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bazzar.picture.converters.PictureConverter;
import ru.bazzar.picture.entities.Picture;
import lombok.extern.slf4j.Slf4j;
import ru.bazzar.picture.service.PictureService;
import ru.bazzar.picture.util.FileResourcesUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;

@Slf4j
@DisplayName("Контроллер картинок должен")
class PictureControllerTest {
    private static final Long PIC_TEST_ID  = 666L;

    private PictureController pictureController;
    private Picture testPicture;
    FileResourcesUtils fileResourcesUtils;

    @BeforeEach
    void setUp() {
        initPicture();
        // создаем объект-заглушку
        PictureService mockService = mock(PictureService.class);
        PictureConverter mockConverter = mock(PictureConverter.class);
        pictureController = new PictureController(mockService, mockConverter);
        // задаем нужное поведение объекта-заглушки
        when(mockService.findById(testPicture.getId())).thenReturn(testPicture);

        fileResourcesUtils = new FileResourcesUtils();
    }

    @Test
    void findPictureByIdAndReturnDto() {

    }

    @Test
    void savePicDtoAndReturnId() {
    }

    @Test
    void findPictureByIdAndResponse() {
    }

    @Test
    void deletePic() {
    }

    @DisplayName("сохранить картинку")
    @Test
    void savePic() {

    }

    private void initPicture() {
        testPicture = new Picture();
        testPicture.setId(PIC_TEST_ID);
        testPicture.setFileName("test.jpg");
        //testPicture.setBytes(new ByteArrayInputStream(new byte[100]).readAllBytes());
        try {
            testPicture.setBytes(Files.readAllBytes(Path.of("pic_example/test.jpeg")));
        } catch (IOException e) {
            log.error("IOException in testPicture.setBytes() method (testing)");
            throw new RuntimeException(e);
        }
    }

}