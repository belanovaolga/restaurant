package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.waiter.model.OrderPositions;
import ru.liga.restaurant.waiter.model.WaiterOrder;
import ru.liga.restaurant.waiter.model.request.OrderPositionsRequest;
import ru.liga.restaurant.waiter.model.response.OrderPositionsResponse;

@Mapper(componentModel = "spring")
public interface OrderPositionsMapper {
    @Mapping(target = "compositionId", ignore = true)
    @Mapping(target = "menu", ignore = true)
    OrderPositions toOrderPositions(OrderPositionsRequest orderPositionsRequest, WaiterOrder waiterOrder);

    @Mapping(target = "menuResponse", source = "menu")
    OrderPositionsResponse toOrderPositionsResponse(OrderPositions orderPositions);

    @Mapping(target = "menuPositionId", source = "menu.id")
    OrderPositionsRequest toOrderPositionsRequest(OrderPositions orderPositions);
}
