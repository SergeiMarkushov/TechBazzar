package ru.bazzar.auth.services;

import ru.bazzar.api.NotificationDto;

import java.util.List;

public interface NotificationService {
    void save(NotificationDto notificationDto);
    List<NotificationDto> findAllByUsername(String username);
    void delete(Long id);
}
