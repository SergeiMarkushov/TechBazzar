package ru.bazzar.cart.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.cart.api.CartDto;
import ru.bazzar.cart.api.StringResponse;
import ru.bazzar.cart.converters.CartConverter;
import ru.bazzar.cart.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Log4j2
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@RequestHeader(name = "username") String username, @PathVariable Long id) {
        cartService.add(username, id);
    }

    @GetMapping("/clear")
    public void clearCart(@RequestHeader(name = "username") String username) {
        cartService.clear(username);
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@RequestHeader(name = "username") String username, @PathVariable Long id) {
        cartService.remove(username, id);
    }

    @GetMapping
    public CartDto getCurrentCart(@RequestHeader(name = "username") String username) {
        return cartConverter.entityToDto(cartService.getCurrentCart(username));
    }

    @GetMapping("/change_quantity")
    public void changeQuantity(@RequestHeader(name = "username") String username, @RequestParam Long productId, @RequestParam Integer delta) {
        System.out.println("в картконтроллере" + delta);
        cartService.changeQuantity(username, productId, delta);
    }

}
