package ru.bazzar.keycloak.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String msg;
    private int code;

    @Override
    public String toString() {
        return "Error{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return code == error.code && Objects.equals(msg, error.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, code);
    }
}
