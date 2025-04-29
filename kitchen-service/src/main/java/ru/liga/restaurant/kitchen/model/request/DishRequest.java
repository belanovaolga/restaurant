package ru.liga.restaurant.kitchen.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для передачи информации о блюде в запросе
 */
@Data
@Schema(description = "Запрос с информацией о блюде")
public class DishRequest {
    @Schema(description = "ID блюда", example = "45")
    private Long dishId;

    @Schema(description = "Количество блюд", example = "3", minimum = "1")
    private Long dishesNumber;
}