package ru.bazzar.telegrambotservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BotConfig {

    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;
}
