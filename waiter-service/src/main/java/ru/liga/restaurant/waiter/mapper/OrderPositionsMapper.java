package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.waiter.model.entity.OrderPositions;
import ru.liga.restaurant.waiter.model.entity.WaiterOrder;
import ru.liga.restaurant.waiter.model.request.OrderPositionsRequest;
import ru.liga.restaurant.waiter.model.response.OrderPositionsResponse;

/**
 * Маппер для преобразования между сущностью OrderPositions и другими
 */
@Mapper(componentModel = "spring")
public interface OrderPositionsMapper {
    /**
     * Преобразует OrderPositionsRequest в OrderPositions
     */
    @Mapping(target = "compositionId", ignore = true)
    @Mapping(target = "menu", ignore = true)
    OrderPositions toOrderPositions(OrderPositionsRequest orderPositionsRequest, WaiterOrder waiterOrder);

    /**
     * Преобразует OrderPositions в OrderPositionsResponse
     */
    @Mapping(target = "menuResponse", source = "menu")
    OrderPositionsResponse toOrderPositionsResponse(OrderPositions orderPositions);

    /**
     * Преобразует OrderPositions в OrderPositionsRequest
     */
    @Mapping(target = "menuPositionId", source = "menu.id")
    OrderPositionsRequest toOrderPositionsRequest(OrderPositions orderPositions);
}
