package ru.bazzar.picture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.picture.api.PictureCreationException;
import ru.bazzar.picture.api.ResourceNotFoundException;
import ru.bazzar.picture.converters.PictureConverter;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;

import java.io.IOException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final PictureConverter pictureConverter;

    public Picture saveMultipartFile(MultipartFile file){
        Picture pic;
        if (file.getSize() > 0) {
            try {
                pic = pictureConverter.multipartToEntity(file);
            } catch (IOException e) {
                log.error("multipartToEntity(file) is failed");
                throw new PictureCreationException("Не удалось конвертировать картинку");
            }
            return save(pic);
        }
        //если что-то не так - вернёт 1L
        return findById(1L);
    }

    public Picture save(Picture picture){
        return pictureRepository.save(picture);
    }


    public Picture findById(Long id){
        return pictureRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Картинка с id: " + id + " - не найдна!")
        );
    }

    public Picture findByFileName(String fileName){
        return pictureRepository.findByFileName(fileName).orElseThrow(
                ()-> new ResourceNotFoundException("Картинка: " + fileName + " - не найдна!")
        );
    }


    public void deleteById(Long id){
        if(id != null && id > 1){
            pictureRepository.deleteById(id);
        }
    }



}