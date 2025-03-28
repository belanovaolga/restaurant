package ru.liga.restaurant.waiter.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@RequiredArgsConstructor
public class OrderAlreadyExistException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
