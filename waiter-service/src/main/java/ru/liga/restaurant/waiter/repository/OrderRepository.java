package ru.liga.restaurant.waiter.repository;

import org.springframework.stereotype.Repository;
import ru.liga.restaurant.waiter.dto.OrderDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    Map<Long, OrderDto> orders = new ConcurrentHashMap<>();

    public List<OrderDto> findAll() {
        return orders.values().stream().toList();
    }

    public Optional<OrderDto> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public OrderDto save(OrderDto orderDto) {
        orders.put(orderDto.getId(), orderDto);
        return orderDto;
    }
}
