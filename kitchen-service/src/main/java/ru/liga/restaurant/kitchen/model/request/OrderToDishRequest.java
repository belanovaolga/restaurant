package ru.liga.restaurant.kitchen.model.request;

import lombok.Data;

@Data
public class OrderToDishRequest {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private Long dishId;
    private Long dishesNumber;
}
