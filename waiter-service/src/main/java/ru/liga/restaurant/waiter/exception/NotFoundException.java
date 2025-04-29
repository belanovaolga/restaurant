package ru.liga.restaurant.waiter.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Исключение, выбрасываемое, когда запрашиваемый ресурс не найден
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@RequiredArgsConstructor
@Schema(description = "Запрашиваемый ресурс не найден")
public class NotFoundException extends RuntimeException {
    /**
     * Сообщение о причине возникновения исключения
     */
    @Schema(description = "Сообщение об ошибке", example = "Запрашиваемый ресурс не найден")
    private final String message;

    /**
     * Возвращаемый HTTP статус
     */
    @Schema(description = "HTTP статус ошибки", example = "NOT_FOUND")
    private final HttpStatus httpStatus;
}
