package ru.bazzar.picture.service;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.util.FileResourcesUtils;

import java.io.InputStream;

@SpringBootTest
@Slf4j
class PictureServiceTest {
    private Picture testPicture;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PictureRepository pictureRepository;

    @BeforeEach
    void setUp() {
        initPicture();
    }

    @Test
    void saveMultipartFile() {
    }

    @Test
    void save() {
        Assertions.assertNotNull(testPicture);
        Assertions.assertNotNull(testPicture.getId());
        Assertions.assertEquals(testPicture.getContentType(), "image/jpeg");
        Assertions.assertEquals(testPicture.getFileName(), "test.jpeg");
        Assertions.assertNotNull(testPicture.getBytes());

        Picture savedPic = pictureRepository.save(testPicture);
        Assertions.assertEquals(savedPic.getFileName(),testPicture.getFileName());
    }

    @Test
    void findById() {
        Long defaultId = 1L;

        Picture foundPic = pictureService.findById(defaultId);
        Assertions.assertNotNull(foundPic);
        Assertions.assertEquals(foundPic.getId(),defaultId);
    }

    @Test
    void findByFileName() {
    }

    @Test
    void deleteById() {
    }

    private void initPicture() {
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        InputStream is = fileResourcesUtils.getFileFromResourceAsStream("pic_example/test.jpeg");
        testPicture = Picture.builder()
                //.id(PIC_TEST_ID) -- id переназначается в БД!
                .contentType("image/jpeg")
                .fileName("test.jpeg")
                .bytes(fileResourcesUtils.convertStreamToByteArr(is))
                .build();

    }
}