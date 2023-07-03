package ru.bazzar.picture.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Рога&Копыта - TechBazzar - (picture-service) - Сервис картинок продукта")
                                .version("1")
                );
    }
}
