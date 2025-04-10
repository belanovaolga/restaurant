package ru.liga.restaurant.waiter.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderToDishRequest {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private List<DishRequest> dishRequest;
}
