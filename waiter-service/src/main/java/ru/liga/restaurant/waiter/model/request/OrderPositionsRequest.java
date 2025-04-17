package ru.liga.restaurant.waiter.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Позиция в заказе")
public class OrderPositionsRequest {
    @Schema(description = "Количество блюд", example = "2", minimum = "1")
    private Long dishNum;
    @Schema(description = "ID позиции в меню", example = "10")
    private Long menuPositionId;
}
