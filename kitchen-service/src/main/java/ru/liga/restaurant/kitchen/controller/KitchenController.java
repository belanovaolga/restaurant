package ru.liga.restaurant.kitchen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurant.kitchen.dto.KitchenDto;
import ru.liga.restaurant.kitchen.request.KitchenRequest;
import ru.liga.restaurant.kitchen.service.KitchenService;

import java.util.List;

@RestController
@RequestMapping("/kitchen")
@RequiredArgsConstructor
public class KitchenController {
    private final KitchenService kitchenService;

    @PostMapping
    public KitchenDto acceptOrder(@RequestBody KitchenRequest kitchenRequest) {
        return kitchenService.acceptOrder(kitchenRequest);
    }

    @PostMapping("/reject")
    public String rejectOrder(@RequestBody KitchenDto kitchenDTO) {
        return kitchenService.rejectOrder(kitchenDTO);
    }

    @GetMapping("/{id}")
    public KitchenDto readyOrder(@PathVariable Long id) {
        return kitchenService.readyOrder(id);
    }

    @GetMapping
    public List<KitchenDto> getKitchenList() {
        return kitchenService.getKitchenList();
    }
}
