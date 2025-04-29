package ru.liga.restaurant.waiter.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO запроса с информацией о заказе и блюдах в нем
 */
@Data
@Builder
public class OrderToDishRequest {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private List<DishRequest> dishRequest;
}
