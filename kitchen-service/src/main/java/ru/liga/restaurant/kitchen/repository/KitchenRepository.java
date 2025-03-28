package ru.liga.restaurant.kitchen.repository;

import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.dto.KitchenDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class KitchenRepository {

    private final Map<Long, KitchenDto> orders = new ConcurrentHashMap<>();

    public Optional<KitchenDto> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<KitchenDto> findAll() {
        return orders.values().stream().toList();
    }

    public KitchenDto save(KitchenDto kitchenDTO) {
        orders.put(kitchenDTO.getId(), kitchenDTO);
        return kitchenDTO;
    }
}

