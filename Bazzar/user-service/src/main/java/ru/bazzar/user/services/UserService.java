package ru.bazzar.user.services;

import ru.bazzar.user.api.UserDto;
import ru.bazzar.user.entities.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String email);
    void save(UserDto userDto);
    Optional<User> findById(Long id);
    User payment(User user, BigDecimal totalPrice);
    void receivingProfit(UserDto userDto);
    void refundProfit(UserDto userDto);
    User upBalance(UserDto userDto);
    List<User> findAll();
    void userBun(Long id);
    User refundPayment(User user, BigDecimal totalPrice);
}
