package ru.liga.restaurant.kitchen.custom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface KitchenOrderCustomRepository {
    void insert(KitchenOrder kitchenOrder);

    List<KitchenOrder> findAll();

    Optional<KitchenOrder> findById(Long id);

    void updateStatus(KitchenOrder kitchenOrder);
}