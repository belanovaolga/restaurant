package ru.liga.restaurant.kitchen.model.response;

import lombok.Data;

@Data
public class OrderToDishResponse {
    private KitchenOrderResponse kitchenOrderResponse;
    private DishResponse dishResponse;
}
