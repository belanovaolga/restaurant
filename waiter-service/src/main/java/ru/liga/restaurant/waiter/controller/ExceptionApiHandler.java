package ru.liga.restaurant.waiter.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.exception.OrderAlreadyExistException;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(OrderAlreadyExistException.class)
    public ResponseStatusException orderAlreadyExistException(OrderAlreadyExistException orderAlreadyExistException) {
        return new ResponseStatusException(orderAlreadyExistException.getHttpStatus(), orderAlreadyExistException.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseStatusException orderNotFoundException(NotFoundException notFoundException) {
        return new ResponseStatusException(notFoundException.getHttpStatus(), notFoundException.getMessage());
    }
}
