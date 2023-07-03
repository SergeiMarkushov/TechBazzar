package ru.bazzar.organization.config;

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
                                .title("Рога&Копыта - TechBazzar - (organization-service)" +
                                        " - Сервис работы с организациями и их логотипами")
                                .version("1")
                );
    }
}
