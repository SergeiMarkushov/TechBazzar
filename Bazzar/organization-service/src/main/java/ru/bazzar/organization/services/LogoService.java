package ru.bazzar.organization.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.bazzar.organization.converters.LogoConverter;
import ru.bazzar.organization.entities.Logo;
import ru.bazzar.organization.repositories.LogoRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LogoService {
    private final LogoRepository repository;
    private final LogoConverter logoConverter;

    public Logo save(MultipartFile file) throws IOException {
        Logo logo = new Logo();
        if (file.getSize() != 0) {
            logo = logoConverter.toLogoEntity(file);
        }
        return repository.save(logo);
    }

}
