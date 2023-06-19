package ru.bazzar.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.auth.api.ResourceNotFoundException;
import ru.bazzar.auth.api.RoleRequest;
import ru.bazzar.auth.api.UserDto;
import ru.bazzar.auth.api.AppError;
import ru.bazzar.auth.converters.UserConverter;
import ru.bazzar.auth.entities.Role;
import ru.bazzar.auth.entities.User;
import ru.bazzar.auth.services.UserServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;
    private final UserConverter userConverter;

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable Long userId) {
        return userConverter.entityToDto(userServiceImpl.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + userId + " не найден!")));
    }

    @GetMapping
    public UserDto findByUsername(@RequestHeader(name = "username") String username) {
        return userConverter.entityToDto(userServiceImpl.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + username + " не найден!")));
    }

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return userServiceImpl.findAll().stream()
                .map(userConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/bun/{id}")
    public void userBun(@PathVariable Long id) {
        userServiceImpl.userBun(id);
    }

    @GetMapping("/is_active/{username}")
    public UserDto isActiveForUser (@PathVariable String username) {
        if (username != null) {
            User user = userServiceImpl.findByEmail(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь " + username + " не найден!"));
            return userConverter.entityToDto(user);
        } else
            return null;
    }

    @PostMapping("/set_role")
    public UserDto setRole(@RequestBody RoleRequest roleRequest) {
        log.info("setRole " + roleRequest);
        return userConverter.entityToDto(userServiceImpl.setRole(roleRequest.getEmail(), roleRequest.getRole()));
    }

    @DeleteMapping("/delete_role")
    public UserDto deleteRole(@RequestBody RoleRequest roleRequest) {
        log.info("Role deleted " + roleRequest);
        return userConverter.entityToDto(userServiceImpl.deleteRole(roleRequest.getEmail(), roleRequest.getRole()));
    }

    @PostMapping("/up_balance")
    public UserDto upBalance(@RequestBody UserDto userDto) {
        return userConverter.entityToDto(userServiceImpl.upBalance(userDto));
    }

    @GetMapping("/get_roles/{username}")
    public List<Role> getRoles(@PathVariable String username) {
        return userServiceImpl.getUserRoles(username);
    }

    @GetMapping("/payment/{total_price}")
    public ResponseEntity<?> payment(@RequestHeader(name = "username") String username, @PathVariable (name = "total_price") BigDecimal totalPrice) {
        User user = userServiceImpl.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + username + " не найден!"));
        if (user.getBalance().compareTo(totalPrice) < 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Не достаточно средств на счете."), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userServiceImpl.payment(user, totalPrice));
    }

    @GetMapping("/refund/{total_price}")
    public ResponseEntity<?> refundPayment(@RequestHeader(name = "username") String username, @PathVariable (name = "total_price") BigDecimal totalPrice) {
        User user = userServiceImpl.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + username + " не найден!"));
        return ResponseEntity.ok(userServiceImpl.refundPayment(user, totalPrice));
    }

    @PostMapping("/change_balance")
    public void receivingProfit(@RequestBody UserDto userDto) {
        userServiceImpl.receivingProfit(userDto);
    }

    @PostMapping("/decrease_balance")
    public void refundProfit(@RequestBody UserDto userDto) {
        userServiceImpl.refundProfit(userDto);
    }
}
