package ru.bazzar.core.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import ru.bazzar.core.api.AccessException;
import ru.bazzar.core.api.AppError;
import ru.bazzar.core.api.ResourceNotFoundException;

import java.util.List;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchAccessException(AccessException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.LOCKED.value(), e.getMessage()), HttpStatus.LOCKED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchConstraintViolationException(ConstraintViolationException e) {
        List<String> errorMessages = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        log.warn(errorMessages.toString());
        return new ResponseEntity<>(new AppError(
                HttpStatus.BAD_REQUEST.value(),
                errorMessages.toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        String errorMessages = e.getMessage() + " - Ограничение - 5MB";
        return new ResponseEntity<>(new AppError(
                HttpStatus.BAD_REQUEST.value(),
                errorMessages),
                HttpStatus.BAD_REQUEST);
    }

}
