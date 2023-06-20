package ru.bazzar.telegrambotservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bazzar.telegrambotservice.api.ChatRequest;
import ru.bazzar.telegrambotservice.api.ChatResponce;
import ru.bazzar.telegrambotservice.config.TelegramMessageHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final TelegramMessageHandler telegramMessageHandler;

    @PostMapping("/process-message")
    public ChatResponce processMessage(@RequestBody ChatRequest request) {
        String userMessage = request.getMessage();
        String botResponce = telegramMessageHandler.processUserMessage(userMessage);
        return new ChatResponce(userMessage, botResponce);
    }

    @PostMapping("/connect-to-human")
    public void connectToHuman() {
        telegramMessageHandler.connectToHuman();
    }
}
