package ru.liga.restaurant.waiter.exception;

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
@Schema(description = "Ошибка сериализации заказа")
public class OrderSerializationException extends RuntimeException {
    @Schema(description = "Сообщение об ошибке сериализации", example = "Не удалось сериализовать объект заказа")
    private final String message;
    @Schema(description = "HTTP статус ошибки", example = "UNPROCESSABLE_ENTITY")
    private final HttpStatus httpStatus;
}