package ru.bazzar.telegrambotservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponce {
    private String userMessage;
    private String botMessage;
}
