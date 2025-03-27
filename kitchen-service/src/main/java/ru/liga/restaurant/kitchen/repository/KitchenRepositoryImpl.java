package ru.liga.restaurant.kitchen.repository;

import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.dto.KitchenDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class KitchenRepositoryImpl implements KitchenRepository {

    Map<Long, KitchenDTO> orders = new ConcurrentHashMap<>();

    @Override
    public Optional<KitchenDTO> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<KitchenDTO> findAll() {
        return orders.values().stream().toList();
    }

    @Override
    public KitchenDTO save(KitchenDTO kitchenDTO) {
        orders.put(kitchenDTO.getId(), kitchenDTO);
        return kitchenDTO;
    }
}
