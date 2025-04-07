package ru.liga.restaurant.kitchen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;
import ru.liga.restaurant.kitchen.service.KitchenService;

@RestController
@RequestMapping("/kitchen")
@RequiredArgsConstructor
public class KitchenController {
    private final KitchenService kitchenService;

    @PostMapping
    public OrderToDishResponse acceptOrder(@RequestBody OrderToDishRequest orderToDishRequest) {
        return kitchenService.acceptOrder(orderToDishRequest);
    }

    @PostMapping("/reject")
    public String rejectOrder(@RequestBody OrderToDishRequest orderToDishRequest) {
        return kitchenService.rejectOrder(orderToDishRequest);
    }

    @GetMapping("/{id}")
    public OrderToDishListResponse readyOrder(@PathVariable Long id) {
        return kitchenService.readyOrder(id);
    }

    @GetMapping
    public OrderToDishListResponse getKitchenList() {
        return kitchenService.getKitchenList();
    }
}
