package ru.bazzar.telegrambotservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bazzar.telegrambotservice.config.BotConfig;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // заглушка
    }

    public String processUserMessage(String userMessage) {
        return "Логика обработки сообщения пользователя и генерации ответа" + userMessage;
    }

    public void connectToHuman() {
        // Логика подключения пользователя к оператору
    }
}
