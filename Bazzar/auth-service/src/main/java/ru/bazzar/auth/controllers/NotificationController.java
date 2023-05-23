package ru.bazzar.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.api.NotificationDto;
import ru.bazzar.auth.services.NotificationServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationServiceImpl notificationServiceImpl;

    @GetMapping
    public List<NotificationDto> findAllByUsername(@RequestHeader String username) {
        return notificationServiceImpl.findAllByUsername(username);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationServiceImpl.delete(id);
    }
}
