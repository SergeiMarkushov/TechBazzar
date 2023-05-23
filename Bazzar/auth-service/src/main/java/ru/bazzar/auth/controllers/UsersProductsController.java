package ru.bazzar.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.api.AppError;
import ru.bazzar.auth.services.UsersProductsServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users_products")
public class UsersProductsController {
    private final UsersProductsServiceImpl usersProductsServiceImpl;

    @GetMapping
    public ResponseEntity<?> purchasedProduct(@RequestHeader(name = "username") String username, @PathVariable Long productId) {
        if (!usersProductsServiceImpl.isPurchasedProduct(username, productId)) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь: " + username + " не приобретал продукт с id: " + productId), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(usersProductsServiceImpl.isPurchasedProduct(username, productId));
    }

    @GetMapping("/save/{productId}")
    public void save(@RequestHeader(name = "username") String username, @PathVariable Long productId) {
        usersProductsServiceImpl.save(username, productId);
    }
}
