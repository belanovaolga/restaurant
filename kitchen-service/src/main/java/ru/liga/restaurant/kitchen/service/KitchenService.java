package ru.liga.restaurant.kitchen.service;

import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;

public interface KitchenService {
    OrderToDishResponse acceptOrder(OrderToDishRequest orderToDishRequest);

    String rejectOrder(OrderToDishRequest orderToDishRequest);

    void readyOrder(Long id);

    OrderToDishListResponse getKitchenList();
}
