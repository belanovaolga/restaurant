package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.response.DishResponse;

/**
 * Маппер для преобразования между сущностью Dish и другими
 */
@Mapper(componentModel = "spring")
public interface DishMapper {
    /**
     * Преобразует сущность Dish в DishResponse
     *
     * @param dish сущность блюда
     * @return DTO с информацией о блюде
     */
    DishResponse toDishResponse(Dish dish);
}