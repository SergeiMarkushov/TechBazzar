package ru.bazzar.picture.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.picture.api.ResourceNotFoundException;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.util.FileResourcesUtils;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
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
    //Small < 1mb
    void test_saveMultipartFileSmall() throws IOException {
        FileResourcesUtils fileResourcesUtilsSmall = new FileResourcesUtils();
        String fileNameSmall = "pic_example/defaultnophotopic.jpg";
        InputStream isSmall = fileResourcesUtilsSmall.getFileFromResourceAsStream(fileNameSmall);

        MultipartFile imageSmall = mock(MultipartFile.class);
        when(imageSmall.getBytes()).thenReturn(isSmall.readAllBytes());
        when(imageSmall.getOriginalFilename()).thenReturn("defaultnophotopic.jpg");
        when(imageSmall.getContentType()).thenReturn("image/jpeg");

        pictureService.saveMultipartFile(imageSmall);

        Assertions.assertEquals(imageSmall.getOriginalFilename()
                ,pictureService.findByFileName("defaultnophotopic.jpg").getFileName());

    }

//    @Test
//    //Big > 5mb (exception!) подтверждает, что в БД не сохранит больше 5mb
//    //<работает только если по одиночке запускаешь почему-то>
//    void test_saveMultipartFileBig() throws IOException {
//        FileResourcesUtils fileResourcesUtilsBig = new FileResourcesUtils();
//        String fileNameBig = "pic_example/big.jpg";
//        InputStream isBig = fileResourcesUtilsBig.getFileFromResourceAsStream(fileNameBig);
//
//        MultipartFile imageBig = mock(MultipartFile.class);
//        when(imageBig.getBytes()).thenReturn(isBig.readAllBytes());
//        when(imageBig.getOriginalFilename()).thenReturn("big.jpg");
//        when(imageBig.getContentType()).thenReturn("image/jpeg");
//
//        pictureService.saveMultipartFile(imageBig);
//
//        Assertions.assertThrows(ResourceNotFoundException.class,
//                ()->{ pictureService.findByFileName(imageBig.getOriginalFilename()); });
//    }

    @Test
    //сохраняем маленькую картинку
    void test_save() {
        Assertions.assertNotNull(testPicture);
        Assertions.assertEquals(testPicture.getContentType(), "image/jpeg");
        Assertions.assertEquals(testPicture.getFileName(), "test.jpeg");
        Assertions.assertNotNull(testPicture.getBytes());

        Picture savedPic = pictureRepository.save(testPicture);
        Assertions.assertEquals(savedPic.getFileName(),testPicture.getFileName());
        Assertions.assertEquals(savedPic.getBytes(),testPicture.getBytes());
    }

    @Test
    //сохраняем большую картинку
    //В настройках сервиса стоит ограничение, это проверка возможности сохранения > 5Mb
    void test_saveBigPic() {
        Assertions.assertNotNull(bigPicture);
        Assertions.assertEquals(bigPicture.getContentType(), "image/jpeg");
        Assertions.assertEquals(bigPicture.getFileName(), "big.jpg");
        Assertions.assertNotNull(bigPicture.getBytes());

        Picture savedPic = pictureRepository.save(bigPicture);
        Assertions.assertEquals(savedPic.getFileName(),bigPicture.getFileName());
        Assertions.assertEquals(savedPic.getBytes(),bigPicture.getBytes());
    }


    @Test
    void test_findById() {
        //ведём поиск по дефолтной фотке
        // (должна быть всегда! "pic_example/defaultnophotopic.jpg")
        // добавляется при первой инициализации БД в PictureConfig.class
        Long defaultId = 1L;

        Picture foundPic = pictureService.findById(defaultId);
        Assertions.assertNotNull(foundPic);
        Assertions.assertEquals(foundPic.getId(),defaultId);
    }

    @Test
    void test_findByFileName() {
        //не используемый метод
        Picture foundPic = pictureService.findByFileName("defaultnophotopic.jpg");
        Assertions.assertNotNull(foundPic);
        Assertions.assertEquals(foundPic.getFileName(), "defaultnophotopic.jpg");
    }

    private void initPictures() {
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        InputStream is = fileResourcesUtils.getFileFromResourceAsStream("pic_example/test.jpeg");
        InputStream isBig = fileResourcesUtils.getFileFromResourceAsStream("pic_example/big.jpg");
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

//    @Test
//    //(exception!)
//    //<работает только если по одиночке запускаешь почему-то>
//    void test_deleteById() {
//        pictureService.save(testPicture);
//        Long id = pictureService.findByFileName(testPicture.getFileName()).getId();
//        pictureService.deleteById(id);
//
//        Assertions.assertThrows(ResourceNotFoundException.class,
//                ()->{ pictureService.findById(id); });
//    }
}