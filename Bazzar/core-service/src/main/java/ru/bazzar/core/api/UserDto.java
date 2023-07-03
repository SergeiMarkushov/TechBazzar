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
    private String fullName;
    private BigDecimal balance;
    private boolean active;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", balance=" + balance +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;
        return isActive() == userDto.isActive() && Objects.equals(getId(), userDto.getId()) && Objects.equals(getUsername(), userDto.getUsername()) && Objects.equals(getFullName(), userDto.getFullName()) && Objects.equals(getBalance(), userDto.getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getFullName(), getBalance(), isActive());
    }
}
