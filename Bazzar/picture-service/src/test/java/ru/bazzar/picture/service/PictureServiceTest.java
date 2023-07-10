//package ru.bazzar.picture.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.bazzar.picture.entities.Picture;
//import ru.bazzar.picture.repositories.PictureRepository;
//import ru.bazzar.picture.util.FileResourcesUtils;
//
//import java.io.InputStream;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@Slf4j
////НЕ ВЫПОЛНЯТЬ НА УДАЛЕННОЙ БД!!!
//class PictureServiceTest {
//    private Picture testPicture;
//    private Picture bigPicture;
//
//    @Autowired
//    private PictureService pictureService;
//
//    @Autowired
//    private PictureRepository pictureRepository;
//
//    @BeforeEach
//    void test_setUp() {
//        initPictures();
//    }
//
//    @Test
//    void test_save() {
//        Assertions.assertNotNull(testPicture);
//        Assertions.assertEquals(testPicture.getContentType(), "image/jpeg");
//        Assertions.assertEquals(testPicture.getFileName(), "test.jpeg");
//        Assertions.assertNotNull(testPicture.getBytes());
//
//        Picture savedPic = pictureRepository.save(testPicture);
//        Assertions.assertEquals(savedPic.getFileName(),testPicture.getFileName());
//        Assertions.assertEquals(savedPic.getBytes(),testPicture.getBytes());
//        pictureRepository.deleteById(savedPic.getId());
//    }
//
//    @Test
//    void test_saveBigPic() {
//        Assertions.assertNotNull(bigPicture);
//        Assertions.assertEquals(bigPicture.getContentType(), "image/jpeg");
//        Assertions.assertEquals(bigPicture.getFileName(), "big.jpg");
//        Assertions.assertNotNull(bigPicture.getBytes());
//
//        Picture savedPic = pictureRepository.save(bigPicture);
//        Assertions.assertEquals(savedPic.getFileName(),bigPicture.getFileName());
//        Assertions.assertEquals(savedPic.getBytes(),bigPicture.getBytes());
//        pictureRepository.deleteById(savedPic.getId());
//    }
//
//    @Test
//    void test_findById() {
//        Long id = pictureService.save(testPicture).getId();
//        Assertions.assertNotNull(testPicture);
//        Assertions.assertEquals(testPicture.getId(),id);
//    }
//
//    @Test
//    void test_deleteById() {
//
//        Long id = pictureService.save(testPicture).getId();
//        pictureService.deleteById(id);
//
//        Assertions.assertThrows(Exception.class,
//                ()->{ pictureService.findById(id); });
//
//    }
//
//    @Test
//    void test_deleteAll() {
//        Assertions.assertThrows(UnsupportedOperationException.class,
//                ()->{ pictureRepository.deleteAll(); });
//    }
//
//
//    private void initPictures() {
//        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
//        InputStream is = fileResourcesUtils.getFileFromResourceAsStream("pic_example/test.jpeg");
//        InputStream isBig = fileResourcesUtils.getFileFromResourceAsStream("pic_example/big.jpg");
//        //до 1Mb
//        testPicture = Picture.builder()
//                .contentType("image/jpeg")
//                .fileName("test.jpeg")
//                .bytes(fileResourcesUtils.convertStreamToByteArr(is))
//                .build();
//        //более 5Mb
//        bigPicture = Picture.builder()
//                .contentType("image/jpeg")
//                .fileName("big.jpg")
//                .bytes(fileResourcesUtils.convertStreamToByteArr(isBig))
//                .build();
//    }
//}