package ru.liga.restaurant.kitchen.model.entity;

import lombok.Data;

@Data
public class OrderToDish {
    private Long kitchenOrderId;
    private Long dishId;
    private Long dishesNumber;
}