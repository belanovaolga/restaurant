package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.response.DishResponse;

@Mapper(componentModel = "spring")
public interface DishMapper {
    DishResponse toDishResponse(Dish dish);
}
