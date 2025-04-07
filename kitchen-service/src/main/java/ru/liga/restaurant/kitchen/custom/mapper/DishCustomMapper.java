package ru.liga.restaurant.kitchen.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.Dish;

@Mapper
@Repository
public interface DishCustomMapper {
    void insert(@Param("dish")Dish dish);
    Dish findById(Long id);
}
