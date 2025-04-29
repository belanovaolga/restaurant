package ru.liga.restaurant.kitchen.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Исключение, выбрасываемое при попытке удалить заказ, который невозможно удалить
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@RequiredArgsConstructor
@Schema(description = "Ошибка удаления заказа")
public class OrderCannotBeDeletedException extends RuntimeException {
    /**
     * Сообщение о причине возникновения исключения
     */
    @Schema(description = "Сообщение об ошибке при удалении заказа", example = "Не удалось отклонить (удалить) заказ")
    private final String message;

    /**
     * Возвращаемый HTTP статус
     */
    @Schema(description = "HTTP статус ошибки", example = "UNPROCESSABLE_ENTITY")
    private final HttpStatus httpStatus;
}
