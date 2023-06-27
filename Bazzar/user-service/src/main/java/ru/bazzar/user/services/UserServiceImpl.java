package ru.bazzar.user.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.user.api.NotificationDto;
import ru.bazzar.user.api.ResourceNotFoundException;
import ru.bazzar.user.api.UserDto;
import ru.bazzar.user.api.UserNotFoundException;
import ru.bazzar.user.entities.User;
import ru.bazzar.user.integrations.NotificationServiceIntegration;
import ru.bazzar.user.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final NotificationServiceIntegration notificationService;

    public Optional<User> findByUsername(String username) {
        return repository.findByUsernameIgnoreCase(username);
    }

    public void save(String username) {
        System.out.println(username);
        Optional<User> userFromDb = repository.findByUsernameIgnoreCase(username);
        System.out.println(userFromDb.isEmpty());
        System.out.println(userFromDb.isPresent());
        if (userFromDb.isEmpty()) {
            System.out.println("if");
            User user = new User();
            user.setUsername(username);
            user.setBalance(new BigDecimal(1000));
            user.setActive(false);
            log.info("Пользователь с username {} сохранен в базу данных!", username);
            repository.save(user);
        }
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User payment(User user, BigDecimal totalPrice) {
        user.setBalance(user.getBalance().subtract(totalPrice));
        return repository.save(user);
    }

    public void receivingProfit(UserDto userDto) {
        User owner = repository.findByUsernameIgnoreCase(userDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с username: " + userDto.getUsername() + " не найден!"));
        User admin = repository.findByUsernameIgnoreCase("n.v.bekhter@mail.ru").orElseThrow(() -> new UserNotFoundException("Нет такого юзера"));
        owner.setBalance(owner.getBalance().add(userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))));
        admin.setBalance(admin.getBalance().add(userDto.getBalance().multiply(new BigDecimal("0.05"))));
        repository.save(admin);
        repository.save(owner);
    }

    public void refundProfit(UserDto userDto) {
        User owner = repository.findByUsernameIgnoreCase(userDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + userDto.getUsername() + " не найден!"));
        User admin = repository.findByUsernameIgnoreCase("n.v.bekhter@mail.ru").orElseThrow(() -> new UserNotFoundException("Нет такого юзера"));
        if (owner.getBalance().compareTo(userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))) < 0) {
            BigDecimal flaw = (userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))).subtract(owner.getBalance());
            if (owner.isActive()) {
                owner.setActive(false);
            }
            NotificationDto notification = new NotificationDto();
            notification.setTitle("Обнаружена задолжность!!!");
            notification.setContent("В процессе возврата средств за ранее приобретенный товар Вашей организации, на Вашем счете " +
                    "оказалось недостаточно средств в размере: " + flaw + ". Ваш аккаунт заблокирован. Необходимо связаться с администратором n.v.bekhter@mail.ru !");
            notification.setSendTo(owner.getUsername());
            notificationService.sendNotification(notification);
        }
        owner.setBalance(owner.getBalance().subtract(userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))));
        admin.setBalance(admin.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05"))));
        repository.save(admin);
        repository.save(owner);
    }

    public User upBalance(UserDto userDto) {
        User user = repository.findByUsernameIgnoreCase(userDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + userDto.getUsername() + " не найден!"));
        user.setBalance(user.getBalance().add(userDto.getBalance()));
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void userBun(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + id + " не найден!"));
        user.setActive(!user.isActive());
        repository.save(user);
    }

    public User refundPayment(User user, BigDecimal totalPrice) {
        user.setBalance(user.getBalance().add(totalPrice));
        return repository.save(user);
    }
}
