package ru.bazzar.auth.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.bazzar.api.NotificationDto;
import ru.bazzar.api.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class NotificationServiceIntegration {
    private final WebClient notifiServiceWebClient;

    public void sendNotification(NotificationDto notificationDto) {
        notifiServiceWebClient.post()
                .uri("api/v1/notification")
                .bodyValue(notificationDto)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.BAD_REQUEST.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Возникла непредвиденная ошибка при отправке уведомления из auth-service."))
                        )
                .bodyToMono(Void.class)
                .block();

    }
}
