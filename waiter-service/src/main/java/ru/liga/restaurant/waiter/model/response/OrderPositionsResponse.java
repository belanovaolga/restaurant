package ru.liga.restaurant.waiter.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO ответа с информацией о позиции в заказе
 */
@Data
@Schema(description = "Позиция в заказе")
public class OrderPositionsResponse {
    @Schema(description = "ID позиции", example = "70")
    private Long compositionId;

    @Schema(description = "Количество блюд", example = "2")
    private Long dishNum;

    @Schema(description = "Информация о блюде из меню")
    private MenuResponse menuResponse;
}