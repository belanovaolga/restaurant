package ru.liga.restaurant.kitchen.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderToDish {
    private KitchenOrder kitchenOrder;
    private Dish dish;
    private Long dishesNumber;
}