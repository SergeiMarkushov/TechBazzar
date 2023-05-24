package ru.bazzar.notifi.converters;

import org.springframework.stereotype.Service;
import ru.bazzar.api.NotificationDto;
import ru.bazzar.notifi.entities.Notification;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class NotificationConverter {
    public NotificationDto entityToDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notification.getId());
        notificationDto.setTitle(notification.getTitle());
        notificationDto.setCreatedAt(notification.getCreatedAt().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        notificationDto.setContent(notification.getContent());
        notificationDto.setSendTo(notification.getSendTo());
        return notificationDto;
    }

    public Notification dtoToEntity(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setTitle(notificationDto.getTitle());
        notification.setContent(notificationDto.getContent());
        notification.setSendTo(notificationDto.getSendTo());
        return notification;
    }
}
