package ru.liga.restaurant.waiter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurant.waiter.dto.OrderDto;
import ru.liga.restaurant.waiter.request.OrderRequest;
import ru.liga.restaurant.waiter.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrderList() {
        return orderService.getOrderList();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/status/{id}")
    public String getStatus(@PathVariable Long id) {
        return orderService.getStatus(id);
    }
}
