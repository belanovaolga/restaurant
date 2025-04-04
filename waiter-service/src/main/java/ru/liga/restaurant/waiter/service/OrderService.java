package ru.liga.restaurant.waiter.service;

import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrderList();

    OrderResponse getOrder(Long id);

    OrderResponse createOrder(OrderRequest orderRequest);

    String getStatus(Long id);
}
