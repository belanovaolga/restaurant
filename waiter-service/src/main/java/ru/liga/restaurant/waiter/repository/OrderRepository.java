package ru.liga.restaurant.waiter.repository;

import ru.liga.restaurant.waiter.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<OrderDto> findAll();
    Optional<OrderDto> findById(Long id);
    OrderDto save(OrderDto orderDto);
}
