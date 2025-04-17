package ru.liga.restaurant.kitchen.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Ответ с информацией о заказе и блюдах")
public class OrderToDishResponse {
    @Schema(description = "Информация о заказе кухни")
    private KitchenOrderResponse kitchenOrderResponse;
    @Schema(description = "Список информации о блюдах")
    private List<DishResponse> dishResponse;
}