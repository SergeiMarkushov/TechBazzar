package ru.bazzar.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.auth.api.NotificationDto;
import ru.bazzar.auth.api.ResourceNotFoundException;
import ru.bazzar.auth.api.UserDto;
import ru.bazzar.auth.entities.Role;
import ru.bazzar.auth.entities.User;
import ru.bazzar.auth.integrations.NotificationServiceIntegration;
import ru.bazzar.auth.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleServiceImpl roleServiceImpl;
    private final NotificationServiceIntegration notificationService;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found.", email)));
        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRoles(Collections.singleton(roleServiceImpl.findRoleByName("ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setBalance(new BigDecimal("1000"));
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<Role> getUserRoles(String username) {
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found.", username)));
        return user.getRoles().stream().toList();
    }

    public User setRole(String username, String role) {
        User user = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с nickname: " + username + " не найден!"));
        user.getRoles().add(roleServiceImpl.findRoleByName(role));
        return userRepository.save(user);
    }

    public User payment(User user, BigDecimal totalPrice) {
        user.setBalance(user.getBalance().subtract(totalPrice));
        return userRepository.save(user);
    }

    public void receivingProfit(UserDto userDto) {
        User owner = userRepository.findByEmailIgnoreCase(userDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + userDto.getEmail() + " не найден!"));
        User admin = userRepository.findByEmailIgnoreCase("n.v.bekhter@mail.ru").get();
        owner.setBalance(owner.getBalance().add(userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))));
        admin.setBalance(admin.getBalance().add(userDto.getBalance().multiply(new BigDecimal("0.05"))));
        userRepository.save(admin);
        userRepository.save(owner);
    }

    public void refundProfit(UserDto userDto) {
        User owner = userRepository.findByEmailIgnoreCase(userDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + userDto.getEmail() + " не найден!"));
        User admin = userRepository.findByEmailIgnoreCase("n.v.bekhter@mail.ru").get();
        if (owner.getBalance().compareTo(userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))) < 0) {
            BigDecimal flaw = (userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))).subtract(owner.getBalance());
            if (owner.isActive()) {
                owner.setActive(false);
            }
            NotificationDto notification = new NotificationDto();
            notification.setTitle("Обнаружена задолжность!!!");
            notification.setContent("В процессе возврата средств за ранее приобретенный товар Вашей организации, на Вашем счете " +
                    "оказалось недостаточно средств в размере: " + flaw + ". Ваш аккаунт заблокирован. Необходимо связаться с администратором n.v.bekhter@mail.ru !");
            notification.setSendTo(owner.getEmail());
            notificationService.sendNotification(notification);
        }
        owner.setBalance(owner.getBalance().subtract(userDto.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05")))));
        admin.setBalance(admin.getBalance().subtract(userDto.getBalance().multiply(new BigDecimal("0.05"))));
        userRepository.save(admin);
        userRepository.save(owner);
    }

    public User upBalance(UserDto userDto) {
        User user = userRepository.findByEmailIgnoreCase(userDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с email: " + userDto.getEmail() + " не найден!"));
        user.setBalance(user.getBalance().add(userDto.getBalance()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void userBun(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id: " + id + " не найден!"));
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public User refundPayment(User user, BigDecimal totalPrice) {
        user.setBalance(user.getBalance().add(totalPrice));
        return userRepository.save(user);
    }

    public Long getUserId(String username) {
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new ResourceNotFoundException("User с email: " + username + " не существует."));
        return user.getId();
    }
}
