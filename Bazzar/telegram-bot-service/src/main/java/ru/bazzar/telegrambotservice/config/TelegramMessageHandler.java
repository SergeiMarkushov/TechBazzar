package ru.bazzar.telegrambotservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.bazzar.telegrambotservice.service.TelegramBot;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TelegramMessageHandler {
    private final TelegramBot bot;
    private final BotConfig config;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            TelegramLongPollingBot telegramBot = new TelegramBot(config);
            telegramBot.clearWebhook();
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    public String processUserMessage(String userMessage) {
        return bot.processUserMessage(userMessage);
    }

    public void connectToHuman() {
        bot.connectToHuman();
    }
}