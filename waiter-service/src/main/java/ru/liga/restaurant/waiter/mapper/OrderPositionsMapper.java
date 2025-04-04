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
    @Mapping(target = "orderNo", source = "waiterOrder")
    @Mapping(target = "menuPositionId", ignore = true)
    OrderPositions toOrderPositions(OrderPositionsRequest orderPositionsRequest, WaiterOrder waiterOrder);

    OrderPositionsResponse toOrderPositionsResponse(OrderPositions orderPositions);
}
