package ru.bazzar.api;

public class AccessException extends RuntimeException {
    public AccessException(String message) {
        super(message);
    }
}