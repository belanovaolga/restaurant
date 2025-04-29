package ru.liga.restaurant.kitchen.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO ответа со списком всех заказов и связанных с ними блюд
 */
@Data
@Builder
@Schema(description = "Список ответов с информацией о заказах и блюдах")
public class OrderToDishListResponse {
    @Schema(description = "Список заказов с блюдами")
    private List<OrderToDishResponseForKitchen> orderToDishResponseForKitchenList;
}