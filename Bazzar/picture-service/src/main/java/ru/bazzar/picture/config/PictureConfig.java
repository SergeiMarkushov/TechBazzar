package ru.bazzar.picture.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bazzar.picture.entities.Picture;
import ru.bazzar.picture.repositories.PictureRepository;
import ru.bazzar.picture.service.PictureService;
import ru.bazzar.picture.util.FileResourcesUtils;

import javax.annotation.PostConstruct;
import java.io.*;


@Configuration
@RequiredArgsConstructor
public class PictureConfig {

}
