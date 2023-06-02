package ru.bazzar.auth.services;

import ru.bazzar.auth.api.UserDto;
import ru.bazzar.auth.entities.Role;
import ru.bazzar.auth.entities.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User save(UserDto userDto);
    Optional<User> findById(Long id);
    List<Role> getUserRoles(String username);
    User setRole(String username, String role);
    User payment(User user, BigDecimal totalPrice);
    void receivingProfit(UserDto userDto);
    void refundProfit(UserDto userDto);
    User upBalance(UserDto userDto);
    List<User> findAll();
    void userBun(Long id);
    User refundPayment(User user, BigDecimal totalPrice);
}
