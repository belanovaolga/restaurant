package ru.liga.restaurant.kitchen.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@RequiredArgsConstructor
@Schema(description = "Недостаточно продуктов для блюда")
public class InsufficientStockException extends RuntimeException {
    @Schema(description = "Сообщение об ошибке", example = "Недостаточно товара на складе")
    private final String message;
    @Schema(description = "HTTP статус ошибки", example = "CONFLICT")
    private final HttpStatus httpStatus;
}