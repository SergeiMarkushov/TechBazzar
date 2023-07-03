package ru.bazzar.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private int statusCode;
    private String message;

    @Override
    public String toString() {
        return "AppError{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppError appError = (AppError) o;
        return statusCode == appError.statusCode && Objects.equals(message, appError.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, message);
    }
}
