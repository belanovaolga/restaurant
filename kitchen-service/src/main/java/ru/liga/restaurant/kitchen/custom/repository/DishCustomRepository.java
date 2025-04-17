package ru.liga.restaurant.kitchen.custom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ru.liga.restaurant.kitchen.model.entity.Dish;

import java.util.Optional;

@Mapper
@Repository
public interface DishCustomRepository {
    void insert(Dish dish);

    Optional<Dish> findById(Long id);

    void update(Dish dish);
}
