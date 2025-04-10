package ru.liga.restaurant.waiter.model.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderToDishResponse {
    private KitchenOrderResponse kitchenOrderResponse;
    private List<DishResponse> dishResponse;
}