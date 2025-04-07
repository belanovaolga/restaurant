package ru.liga.restaurant.kitchen.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.kitchen.exception.InsufficientStockException;
import ru.liga.restaurant.kitchen.exception.OrderAlreadyExistException;
import ru.liga.restaurant.kitchen.exception.OrderNotFoundException;
import ru.liga.restaurant.kitchen.exception.OrderNotReadyException;

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

    @ExceptionHandler(OrderNotReadyException.class)
    public ResponseStatusException orderNotReadyException(OrderNotReadyException orderNotReadyException) {
        return new ResponseStatusException(orderNotReadyException.getHttpStatus(), orderNotReadyException.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseStatusException insufficientStockException(InsufficientStockException insufficientStockException) {
        return new ResponseStatusException(insufficientStockException.getHttpStatus(), insufficientStockException.getMessage());
    }
}
