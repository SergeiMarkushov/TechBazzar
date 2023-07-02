package ru.bazzar.user.api;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private BigDecimal balance;
    private boolean active;
}
