package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;
import ru.liga.restaurant.kitchen.model.request.DishRequest;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.DishResponse;
import ru.liga.restaurant.kitchen.model.response.KitchenOrderResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderToDishMapper {
    OrderToDishResponse toOrderToDishResponse(KitchenOrderResponse kitchenOrderResponse, List<DishResponse> dishResponse);

    @Mapping(target = "dishesNumber", source = "dishRequest.dishesNumber")
    @Mapping(target = "dishId", source = "dishRequest.dishId")
    OrderToDish toOrderToDish(OrderToDishRequest orderToDishRequest, DishRequest dishRequest);
}
