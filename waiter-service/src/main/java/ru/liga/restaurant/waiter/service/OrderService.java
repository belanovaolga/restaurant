package ru.liga.restaurant.waiter.service;

import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;
import ru.liga.restaurant.waiter.model.response.OrderResponseList;
import ru.liga.restaurant.waiter.model.response.StatusResponse;

public interface OrderService {
    OrderResponseList getOrderList(Integer pageNumber, Integer size);

    OrderResponse getOrder(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    StatusResponse getStatus(Long id);

    void orderReady(Long id);
}
