package ru.liga.restaurant.waiter.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.waiter.exception.OrderAlreadyExist;
import ru.liga.restaurant.waiter.exception.OrderNotFound;

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
}
