package ru.liga.restaurant.kitchen.repository;

import ru.liga.restaurant.kitchen.dto.KitchenDTO;

import java.util.List;
import java.util.Optional;

public interface KitchenRepository {
    Optional<KitchenDTO> findById(Long id);
    List<KitchenDTO> findAll();
    KitchenDTO save(KitchenDTO kitchenDTO);
}
