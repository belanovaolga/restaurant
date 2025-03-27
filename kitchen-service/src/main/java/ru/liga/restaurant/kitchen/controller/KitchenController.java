package ru.liga.restaurant.kitchen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.restaurant.kitchen.dao.KitchenDao;
import ru.liga.restaurant.kitchen.dto.KitchenDTO;
import ru.liga.restaurant.kitchen.service.KitchenService;

import java.util.List;

@RestController
@RequestMapping("/kitchen")
@RequiredArgsConstructor
public class KitchenController {
    private final KitchenService kitchenService;

    @PostMapping
    public ResponseEntity<KitchenDTO> acceptOrder(
            @RequestBody KitchenDao kitchenDao
    ) {
        return ResponseEntity.ok(kitchenService.acceptOrder(kitchenDao));
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectOrder(
            @RequestBody KitchenDTO kitchenDTO) {
        return ResponseEntity.ok(kitchenService.rejectOrder(kitchenDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KitchenDTO> readyOrder(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(kitchenService.readyOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<KitchenDTO>> getKitchenList() {
        return ResponseEntity.ok(kitchenService.getKitchenList());
    }
}
