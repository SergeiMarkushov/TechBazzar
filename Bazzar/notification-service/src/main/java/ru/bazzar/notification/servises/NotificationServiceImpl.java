package ru.bazzar.notification.servises;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bazzar.notification.api.NotificationDto;
import ru.bazzar.notification.converters.NotificationConverter;
import ru.bazzar.notification.entities.Notification;
import ru.bazzar.notification.repositories.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;
    private final NotificationConverter converter;

    public void save(NotificationDto notificationDto) {
        Notification notification = converter.dtoToEntity(notificationDto);
        repository.save(notification);
    }

    public List<NotificationDto> findAllByUsername(String username) {
        return repository.findAllBySendTo(username)
                .stream()
                .map(converter::entityToDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
