package ru.liga.restaurant.kitchen.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@RequiredArgsConstructor
public class OrderAlreadyExist extends RuntimeException {
    private final String message;
    private final Integer code;
}
