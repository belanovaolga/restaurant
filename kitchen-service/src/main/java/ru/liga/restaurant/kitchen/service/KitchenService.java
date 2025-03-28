package ru.liga.restaurant.kitchen.service;

import ru.liga.restaurant.kitchen.dto.KitchenDto;
import ru.liga.restaurant.kitchen.request.KitchenRequest;

import java.util.List;

public interface KitchenService {
    KitchenDto acceptOrder(KitchenRequest kitchenRequest);
    String rejectOrder(KitchenDto kitchenDTO);
    KitchenDto readyOrder(Long id);
    List<KitchenDto> getKitchenList();
}
