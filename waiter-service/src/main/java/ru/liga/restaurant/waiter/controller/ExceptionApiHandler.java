package ru.liga.restaurant.waiter.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.exception.OrderSerializationException;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseStatusException orderNotFoundException(NotFoundException notFoundException) {
        return new ResponseStatusException(notFoundException.getHttpStatus(), notFoundException.getMessage());
    }

    @ExceptionHandler(OrderSerializationException.class)
    public ResponseStatusException orderSerializationException(OrderSerializationException orderSerializationException) {
        return new ResponseStatusException(orderSerializationException.getHttpStatus(), orderSerializationException.getMessage());
    }
}
