package ru.liga.restaurant.waiter.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@RequiredArgsConstructor
public class OrderNotFound extends RuntimeException {
    private final String message;
    private final Integer code;
}
