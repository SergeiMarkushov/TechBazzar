package ru.bazzar.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bazzar.user.api.ResourceNotFoundException;
import ru.bazzar.user.api.UserDto;
import ru.bazzar.user.api.AppError;
import ru.bazzar.user.converters.UserConverter;
import ru.bazzar.user.entities.User;
import ru.bazzar.user.services.UserServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl service;
    private final UserConverter converter;

    @PostMapping("/create")
    public void checkAndCreate(@RequestBody UserDto userDto) {
        System.out.println(userDto.getUsername() + " " + userDto.getFullName());
        service.save(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable Long userId) {
        return converter.entityToDto(service.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + userId + " не найден!")));
    }

    @GetMapping
    public UserDto findByUsername(@RequestHeader(name = "username") String username) {
        return converter.entityToDto(service.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + username + " не найден!")));
    }

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return service.findAll().stream()
                .map(converter::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/bun/{id}")
    public void userBun(@PathVariable Long id) {
        service.userBun(id);
    }

    @GetMapping("/is_active/{username}")
    public UserDto isActiveForUser (@PathVariable String username) {
        if (username != null) {
            User user = service.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь " + username + " не найден!"));
            return converter.entityToDto(user);
        } else
            return null;
    }

    @PostMapping("/up_balance")
    public UserDto upBalance(@RequestBody UserDto userDto) {
        return converter.entityToDto(service.upBalance(userDto));
    }

    @GetMapping("/payment/{total_price}")
    public ResponseEntity<?> payment(@RequestHeader(name = "username") String username, @PathVariable (name = "total_price") BigDecimal totalPrice) {
        User user = service.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + username + " не найден!"));
        if (user.getBalance().compareTo(totalPrice) < 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.PAYMENT_REQUIRED.value(), "Не достаточно средств на счете."), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.payment(user, totalPrice));
    }

    @GetMapping("/refund/{total_price}")
    public ResponseEntity<?> refundPayment(@RequestHeader(name = "username") String username, @PathVariable (name = "total_price") BigDecimal totalPrice) {
        User user = service.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + username + " не найден!"));
        return ResponseEntity.ok(service.refundPayment(user, totalPrice));
    }

    @PostMapping("/change_balance")
    public void receivingProfit(@RequestBody UserDto userDto) {
        service.receivingProfit(userDto);
    }

    @PostMapping("/decrease_balance")
    public void refundProfit(@RequestBody UserDto userDto) {
        service.refundProfit(userDto);
    }
}
