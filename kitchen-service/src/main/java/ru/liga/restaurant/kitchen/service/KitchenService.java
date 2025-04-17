package ru.liga.restaurant.kitchen.service;

import ru.liga.restaurant.kitchen.model.request.OrderRequest;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;

public interface KitchenService {
    OrderToDishResponse acceptOrder(OrderToDishRequest orderToDishRequest);

    void rejectOrder(OrderRequest orderRequest);

    void readyOrder(Long id);

    OrderToDishListResponse getKitchenList(Integer pageNumber, Integer size);
}
