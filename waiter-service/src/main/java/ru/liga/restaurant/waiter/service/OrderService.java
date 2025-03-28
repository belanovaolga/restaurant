package ru.liga.restaurant.waiter.service;

import ru.liga.restaurant.waiter.dto.OrderDto;
import ru.liga.restaurant.waiter.request.OrderRequest;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrderList();
    OrderDto getOrder(Long id);
    OrderDto createOrder(OrderRequest orderRequest);
    String getStatus(Long id);
}
