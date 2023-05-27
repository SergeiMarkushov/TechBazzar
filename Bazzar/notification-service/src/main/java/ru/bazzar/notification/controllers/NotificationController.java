package ru.bazzar.notification.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.notification.api.NotificationDto;
import ru.bazzar.notification.config.MailConfig;
import ru.bazzar.notification.servises.NotificationServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationServiceImpl notificationServiceImpl;
    private final MailConfig myMailSender;

    @GetMapping
    public List<NotificationDto> findAllByUsername(@RequestHeader String username) {
        return notificationServiceImpl.findAllByUsername(username);
    }

    @PostMapping
    public void sendNotification(@RequestBody NotificationDto notificationDto) {
        myMailSender.sendMailNotification(notificationDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationServiceImpl.delete(id);
    }
}
