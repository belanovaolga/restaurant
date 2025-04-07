package ru.liga.restaurant.kitchen.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface KitchenOrderCustomMapper {
    int insert(@Param("order") KitchenOrder kitchenOrder);

    List<KitchenOrder> findAll();

    Optional<KitchenOrder> findById(Long id);
}