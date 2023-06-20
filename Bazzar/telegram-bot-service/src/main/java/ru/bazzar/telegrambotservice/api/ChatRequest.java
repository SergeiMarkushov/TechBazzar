package ru.bazzar.telegrambotservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRequest {
    private String message;

}
