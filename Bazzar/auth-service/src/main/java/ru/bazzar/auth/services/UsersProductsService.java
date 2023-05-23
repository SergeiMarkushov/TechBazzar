package ru.bazzar.auth.services;

public interface UsersProductsService {
    void save(String username, Long productId);
    boolean isPurchasedProduct(String username, Long productId);
}
