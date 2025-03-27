package ru.liga.restaurant.kitchen.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.kitchen.exception.OrderAlreadyExist;
import ru.liga.restaurant.kitchen.exception.OrderNotFound;
import ru.liga.restaurant.kitchen.exception.OrderNotReady;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(OrderAlreadyExist.class)
    public ResponseStatusException orderAlreadyExistException(OrderAlreadyExist orderAlreadyExist) {
        return new ResponseStatusException(HttpStatusCode.valueOf(orderAlreadyExist.getCode()), orderAlreadyExist.getMessage());
    }

    @ExceptionHandler(OrderNotFound.class)
    public ResponseStatusException orderNotFoundException(OrderNotFound orderNotFound) {
        return new ResponseStatusException(HttpStatusCode.valueOf(orderNotFound.getCode()), orderNotFound.getMessage());
    }

    @ExceptionHandler(OrderNotReady.class)
    public ResponseStatusException orderNotReadyException(OrderNotReady orderNotReady) {
        return new ResponseStatusException(HttpStatusCode.valueOf(orderNotReady.getCode()), orderNotReady.getMessage());
    }
}
