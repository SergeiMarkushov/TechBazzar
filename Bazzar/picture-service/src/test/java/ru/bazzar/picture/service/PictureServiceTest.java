package ru.bazzar.picture.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.bazzar.picture.api.ResourceNotFoundException;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.util.FileResourcesUtils;

import java.io.InputStream;

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
    void setUp() {
        initPictures();
    }

    @Test
    //!
    void saveMultipartFile() {
    }

    @Test
    //сохраняем маленькую картинку
    void save() {
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
    void saveBigPic() {
        Assertions.assertNotNull(bigPicture);
        Assertions.assertEquals(bigPicture.getContentType(), "image/jpeg");
        Assertions.assertEquals(bigPicture.getFileName(), "big.jpg");
        Assertions.assertNotNull(bigPicture.getBytes());

        Picture savedPic = pictureRepository.save(bigPicture);
        Assertions.assertEquals(savedPic.getFileName(),bigPicture.getFileName());
        Assertions.assertEquals(savedPic.getBytes(),bigPicture.getBytes());
    }


    @Test
    void findById() {
        //ведём поиск по дефолтной фотке
        // (должна быть всегда! "pic_example/defaultnophotopic.jpg")
        // добавляется при первой инициализации БД в PictureConfig.class
        Long defaultId = 1L;

        Picture foundPic = pictureService.findById(defaultId);
        Assertions.assertNotNull(foundPic);
        Assertions.assertEquals(foundPic.getId(),defaultId);
    }

    @Test
    void findByFileName() {
        //не используемый метод
        Picture foundPic = pictureService.findByFileName("defaultnophotopic.jpg");
        Assertions.assertNotNull(foundPic);
        Assertions.assertEquals(foundPic.getFileName(), "defaultnophotopic.jpg");
    }

    @Test
    void deleteById() {
        pictureService.save(testPicture);
        Long id = pictureService.findByFileName(testPicture.getFileName()).getId();
        pictureService.deleteById(id);

      Assertions.assertThrows(ResourceNotFoundException.class,
            ()->{ pictureService.findById(id); });
    }

    private void initPictures() {
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        InputStream is = fileResourcesUtils.getFileFromResourceAsStream("pic_example/test.jpeg");
        InputStream isBig = fileResourcesUtils.getFileFromResourceAsStream("pic_example/big.jpg");
        testPicture = Picture.builder()
                .contentType("image/jpeg")
                .fileName("test.jpeg")
                .bytes(fileResourcesUtils.convertStreamToByteArr(is))
                .build();
        bigPicture = Picture.builder()
                .contentType("image/jpeg")
                .fileName("big.jpg")
                .bytes(fileResourcesUtils.convertStreamToByteArr(isBig))
                .build();
    }
}