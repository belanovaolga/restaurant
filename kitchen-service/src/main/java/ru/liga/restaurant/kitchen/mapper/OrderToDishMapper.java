package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.DishResponse;
import ru.liga.restaurant.kitchen.model.response.KitchenOrderResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;

@Mapper(componentModel = "spring")
public interface OrderToDishMapper {
    OrderToDishResponse toOrderToDishResponse(KitchenOrderResponse kitchenOrderResponse, DishResponse dishResponse);

    OrderToDish toOrderToDish(OrderToDishRequest orderToDishRequest);
}
