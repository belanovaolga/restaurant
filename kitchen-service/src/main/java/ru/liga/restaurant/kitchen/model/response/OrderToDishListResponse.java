package ru.liga.restaurant.kitchen.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Список ответов с информацией о заказах и блюдах")
public class OrderToDishListResponse {
    @Schema(description = "Список заказов с блюдами")
    private List<OrderToDishResponseForKitchen> orderToDishResponseForKitchenList;
}