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
public class UsersProductsServiceImpl implements UsersProductsService {
    private final UsersProductsRepository repository;
    private final UserServiceImpl userServiceImpl;

    public void save(String username, Long productId) {
        UsersProductsList userProducts = UsersProductsList.builder()
                .userId(userServiceImpl.getUserId(username))
                .productId(productId)
                .build();
        repository.save(userProducts);
    }

    public boolean isPurchasedProduct(String username, Long productId) {
        Optional<UsersProductsList> userProduct = repository.findByUserIdAndProductId(userServiceImpl.getUserId(username), productId);
        return userProduct.isEmpty();
    }
}
