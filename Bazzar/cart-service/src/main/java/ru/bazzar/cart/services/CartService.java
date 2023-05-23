package ru.bazzar.cart.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.bazzar.api.ProductDto;
import ru.bazzar.cart.integrations.ProductServiceIntegration;
import ru.bazzar.cart.model.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart)redisTemplate.opsForValue().get(targetUuid);
    }

    public void add(String uuid, Long productId) {
        execute(uuid, cart -> cart.add(productServiceIntegration.getProductById(productId)));
    }

    public void remove(String uuid, Long productId) {
        execute(uuid, cart -> cart.remove(productId));
    }

    public void clear(String uuid) {
        execute(uuid, Cart::clear);
    }

    public void changeQuantity(String uuid, Long productId, Integer delta) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        execute(uuid, cart -> cart.changeQuantity(product,delta));
    }
    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }

    public void updateCart(String uuid, Cart cart) {
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }
}
