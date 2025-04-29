package ru.liga.restaurant.waiter.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO ответа с информацией о блюде из меню
 */
@Data
@Schema(description = "Информация о позиции меню")
public class MenuResponse {
    @Schema(description = "ID позиции из меню", example = "10")
    private Long id;

    @Schema(description = "Название блюда", example = "Цезарь")
    private String dishName;

    @Schema(description = "Стоимость блюда", example = "1250.50", minimum = "0")
    private Double dishCost;
}