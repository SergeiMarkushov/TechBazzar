package ru.bazzar.core.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private BigDecimal balance;
    private boolean active;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return active == userDto.active && Objects.equals(id, userDto.id) && Objects.equals(username, userDto.username) && Objects.equals(balance, userDto.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, balance, active);
    }
}
