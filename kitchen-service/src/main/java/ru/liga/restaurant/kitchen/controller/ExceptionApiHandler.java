package ru.liga.restaurant.kitchen.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.kitchen.exception.InsufficientStockException;
import ru.liga.restaurant.kitchen.exception.NotFoundException;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseStatusException orderNotFoundException(NotFoundException notFoundException) {
        return new ResponseStatusException(notFoundException.getHttpStatus(), notFoundException.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseStatusException insufficientStockException(InsufficientStockException insufficientStockException) {
        return new ResponseStatusException(insufficientStockException.getHttpStatus(), insufficientStockException.getMessage());
    }
}
