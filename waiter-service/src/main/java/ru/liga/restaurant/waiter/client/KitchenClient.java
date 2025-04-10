package ru.liga.restaurant.waiter.client;

import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;
import ru.liga.restaurant.waiter.model.response.OrderToDishResponse;

public interface KitchenClient {
    OrderToDishResponse acceptOrder(OrderToDishRequest request);
}
