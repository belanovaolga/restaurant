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
@Schema(description = "Запрашиваемый ресурс не найден")
public class NotFoundException extends RuntimeException {
    @Schema(description = "Сообщение об ошибке", example = "Запрашиваемый ресурс не найден")
    private final String message;
    @Schema(description = "HTTP статус ошибки", example = "NOT_FOUND")
    private final HttpStatus httpStatus;
}
