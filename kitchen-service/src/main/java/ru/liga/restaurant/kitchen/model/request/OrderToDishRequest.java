package ru.liga.restaurant.kitchen.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос на передачу заказа на кухню")
public class OrderToDishRequest {
    @Schema(description = "ID заказа на кухне", example = "45")
    private Long kitchenOrderId;
    @Schema(description = "Номер заказа у официанта", example = "78")
    private Long waiterOrderNo;
    @Schema(description = "Список блюд в заказе")
    private List<DishRequest> dishRequest;
}
