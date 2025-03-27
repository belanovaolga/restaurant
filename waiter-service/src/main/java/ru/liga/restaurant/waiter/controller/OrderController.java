package ru.liga.restaurant.waiter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurant.waiter.dao.OrderDao;
import ru.liga.restaurant.waiter.dto.OrderDto;
import ru.liga.restaurant.waiter.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderList() {
        return ResponseEntity.ok(orderService.getOrderList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody OrderDao orderDao
    ) {
        return ResponseEntity.ok(orderService.createOrder(orderDao));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> getStatus(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(orderService.getStatus(id));
    }
}
