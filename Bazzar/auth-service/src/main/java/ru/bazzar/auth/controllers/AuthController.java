package ru.bazzar.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.api.*;
import ru.bazzar.auth.converters.UserConverter;
import ru.bazzar.auth.entities.Role;
import ru.bazzar.auth.entities.User;
import ru.bazzar.auth.mail.MyMailSender;
import ru.bazzar.auth.services.UserServiceImpl;
import ru.bazzar.auth.utils.JwtTokenUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserServiceImpl userServiceImpl;
    private final UserConverter userConverter;
    private final MyMailSender myMailSender;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest request) {
        log.info("Auth request: [{}, {}]", request.getUsername(), request.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = userServiceImpl.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        if (userServiceImpl.findByEmail(userDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(userServiceImpl.save(userDto));
    }

    @GetMapping("/users/{userId}")
    public UserDto findById(@PathVariable Long userId) {
        return userConverter.entityToDto(userServiceImpl.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + userId + " не найден!")));
    }

    @GetMapping("/users")
    public UserDto findById(@RequestHeader(name = "username") String username) {
        return userConverter.entityToDto(userServiceImpl.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + username + " не найден!")));
    }

    @GetMapping("/users/all")
    public List<UserDto> findAll() {
        return userServiceImpl.findAll().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/users/bun/{id}")
    public void userBun(@PathVariable Long id) {
        userServiceImpl.userBun(id);
    }

    @GetMapping("/users/is_active/{username}")
    public UserDto isActiveForUser (@PathVariable String username) {
        if (username != null) {
            User user = userServiceImpl.findByEmail(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь " + username + " не найден!"));
            return userConverter.entityToDto(user);
        } else
            return null;
    }

    @PostMapping("/users/set_role")
    public UserDto setRole(@RequestBody RoleRequest roleRequest) {
        System.out.println("setRole " + roleRequest);
        return userConverter.entityToDto(userServiceImpl.setRole(roleRequest.getEmail(), roleRequest.getRole()));
    }

    @PostMapping("/users/up_balance")
    public UserDto upBalance(@RequestBody UserDto userDto) {
        return userConverter.entityToDto(userServiceImpl.upBalance(userDto));
    }

    @PostMapping("/users/notification")
    public void sendNotification(@RequestBody NotificationDto notificationDto) {
        myMailSender.sendMailNotification(notificationDto);
    }

    @GetMapping("/users/get_roles/{username}")
    public List<Role> getRoles(@PathVariable String username) {
        return userServiceImpl.getUserRoles(username);
    }

    @GetMapping("/users/payment/{total_price}")
    public ResponseEntity<?> payment(@RequestHeader(name = "username") String username, @PathVariable (name = "total_price") BigDecimal totalPrice) {
        User user = userServiceImpl.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + username + " не найден!"));
        if (user.getBalance().compareTo(totalPrice) < 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Не достаточно средств на счете."), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userServiceImpl.payment(user, totalPrice));
    }

    @GetMapping("/users/refund/{total_price}")
    public ResponseEntity<?> refundPayment(@RequestHeader(name = "username") String username, @PathVariable (name = "total_price") BigDecimal totalPrice) {
        User user = userServiceImpl.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + username + " не найден!"));
        return ResponseEntity.ok(userServiceImpl.refundPayment(user, totalPrice));
    }

    @PostMapping("/users/change_balance")
    public void receivingProfit(@RequestBody UserDto userDto) {
        userServiceImpl.receivingProfit(userDto);
    }

    @PostMapping("/users/decrease_balance")
    public void refundProfit(@RequestBody UserDto userDto) {
        userServiceImpl.refundProfit(userDto);
    }
}
