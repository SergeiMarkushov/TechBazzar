package ru.bazzar.picture.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.webresources.FileResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.picture.api.ResourceNotFoundException;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.util.FileResourcesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
//НЕ ВЫПОЛНЯТЬ НА УДАЛЕННОЙ БД!!!
class PictureServiceTest {
    private Picture testPicture;
    private Picture bigPicture;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PictureRepository pictureRepository;

    @BeforeEach
    void test_setUp() {
        initPictures();
    }

    @Test
    void test_save() {
        Assertions.assertNotNull(testPicture);
        Assertions.assertEquals(testPicture.getContentType(), "image/jpeg");
        Assertions.assertEquals(testPicture.getFileName(), "test.jpeg");
        Assertions.assertNotNull(testPicture.getBytes());

        Picture savedPic = pictureRepository.save(testPicture);
        Assertions.assertEquals(savedPic.getFileName(),testPicture.getFileName());
        Assertions.assertEquals(savedPic.getBytes(),testPicture.getBytes());
        pictureRepository.deleteById(savedPic.getId());
    }

    @Test
    void test_saveBigPic() {
        Assertions.assertNotNull(bigPicture);
        Assertions.assertEquals(bigPicture.getContentType(), "image/jpeg");
        Assertions.assertEquals(bigPicture.getFileName(), "big.jpg");
        Assertions.assertNotNull(bigPicture.getBytes());

        Picture savedPic = pictureRepository.save(bigPicture);
        Assertions.assertEquals(savedPic.getFileName(),bigPicture.getFileName());
        Assertions.assertEquals(savedPic.getBytes(),bigPicture.getBytes());
        pictureRepository.deleteById(savedPic.getId());
    }

    @Test
    void test_findById() {
        Long id = pictureService.save(testPicture).getId();
        Assertions.assertNotNull(testPicture);
        Assertions.assertEquals(testPicture.getId(),id);
    }

    @Test
    void test_deleteById() {

        Long id = pictureService.save(testPicture).getId();
        pictureService.deleteById(id);

        Assertions.assertThrows(Exception.class,
                ()->{ pictureService.findById(id); });

    }

    @Test
    void test_deleteAll() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                ()->{ pictureRepository.deleteAll(); });
    }

    @Test
    void test_deleteByDefaultId() {

        Assertions.assertThrows(UnsupportedOperationException.class,
                ()->{ pictureRepository.deleteById(1L); });
    }

    private void initPictures() {
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        InputStream is = fileResourcesUtils.getFileFromResourceAsStream("pic_example/test.jpeg");
        InputStream isBig = fileResourcesUtils.getFileFromResourceAsStream("pic_example/big.jpg");
        InputStream isDef = fileResourcesUtils.getFileFromResourceAsStream("pic_example/defaultnophotopic.jpg");
        //до 1Mb
        testPicture = Picture.builder()
                .contentType("image/jpeg")
                .fileName("test.jpeg")
                .bytes(fileResourcesUtils.convertStreamToByteArr(is))
                .build();
        //более 5Mb
        bigPicture = Picture.builder()
                .contentType("image/jpeg")
                .fileName("big.jpg")
                .bytes(fileResourcesUtils.convertStreamToByteArr(isBig))
                .build();
    }
}