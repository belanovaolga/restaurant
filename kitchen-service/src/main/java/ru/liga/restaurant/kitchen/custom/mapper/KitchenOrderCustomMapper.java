package ru.liga.restaurant.kitchen.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;

import java.util.List;

@Mapper
@Repository
public interface KitchenOrderCustomMapper {
    int insert(@Param("order")KitchenOrder kitchenOrder);
    List<KitchenOrder> findAll();
    KitchenOrder findById(Long id);
}
