package ru.bazzar.auth.api;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String email;
    private String role;

    @Override
    public String toString() {
        return "RoleRequest{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleRequest that = (RoleRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, role);
    }
}
