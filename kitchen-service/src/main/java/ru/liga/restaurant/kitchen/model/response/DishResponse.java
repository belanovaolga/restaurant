package ru.liga.restaurant.kitchen.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO ответа с информацией о блюде
 */
@Data
@Schema(description = "Информация о блюде")
public class DishResponse {
    @Schema(description = "ID блюда", example = "45")
    private Long dishId;

    @Schema(description = "Краткое название блюда", example = "Цезарь")
    private String shortName;
}