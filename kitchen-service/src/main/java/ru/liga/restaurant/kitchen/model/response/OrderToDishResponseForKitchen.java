package ru.liga.restaurant.kitchen.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Ответ с информацией о заказе и блюдах")
public class OrderToDishResponseForKitchen {
    @Schema(description = "Информация о заказе кухни")
    private KitchenOrderResponse kitchenOrderResponse;
    @Schema(description = "Список информации о блюде")
    private DishResponse dishResponse;
}