package ru.bazzar.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bazzar.api.ResourceNotFoundException;
import ru.bazzar.auth.entities.User;
import ru.bazzar.auth.entities.UsersProductsList;
import ru.bazzar.auth.repositories.UsersProductsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersProductsService {
    private final UsersProductsRepository repository;
    private final UserService userService;

    private Long getUserId(String username) {
        User user = userService.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User с email: " + username + " не существует."));
        return user.getId();
    }

    public void save(String username, Long productId) {
        UsersProductsList userProducts = UsersProductsList.builder()
                .userId(getUserId(username))
                .productId(productId)
                .build();
        repository.save(userProducts);
    }

    public boolean purchasedProduct(String username, Long productId) {
        Optional<UsersProductsList> userProduct = repository.findByUserIdAndProductId(getUserId(username), productId);
        return userProduct.isEmpty();
    }
}
