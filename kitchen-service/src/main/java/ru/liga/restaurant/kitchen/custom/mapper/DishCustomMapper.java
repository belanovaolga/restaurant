package ru.liga.restaurant.kitchen.custom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.Dish;

import java.util.Optional;

@Mapper
@Repository
public interface DishCustomMapper {
    void insert(Dish dish);

    Optional<Dish> findById(Long id);

    void update(Dish dish);
}
