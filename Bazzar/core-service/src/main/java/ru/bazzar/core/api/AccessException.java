package ru.bazzar.core.api;

public class AccessException extends RuntimeException {
    public AccessException(String message) {
        super(message);
    }
}