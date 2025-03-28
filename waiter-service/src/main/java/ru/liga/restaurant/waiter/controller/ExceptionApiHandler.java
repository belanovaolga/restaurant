package ru.liga.restaurant.waiter.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.waiter.exception.OrderAlreadyExistException;
import ru.liga.restaurant.waiter.exception.OrderNotFoundException;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(OrderAlreadyExistException.class)
    public ResponseStatusException orderAlreadyExistException(OrderAlreadyExistException orderAlreadyExistException) {
        return new ResponseStatusException(orderAlreadyExistException.getHttpStatus(), orderAlreadyExistException.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseStatusException orderNotFoundException(OrderNotFoundException orderNotFoundException) {
        return new ResponseStatusException(orderNotFoundException.getHttpStatus(), orderNotFoundException.getMessage());
    }
}
