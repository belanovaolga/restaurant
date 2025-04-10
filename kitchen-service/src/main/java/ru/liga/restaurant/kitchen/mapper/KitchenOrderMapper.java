package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.KitchenOrderResponse;

@Mapper(componentModel = "spring")
public interface KitchenOrderMapper {
    KitchenOrderResponse toKitchenOrderResponse(KitchenOrder kitchenOrder);

    @Mapping(target = "kitchenStatus", expression = "java(ru.liga.restaurant.kitchen.model.enums.KitchenStatus.AWAITING)")
    @Mapping(target = "createDate", expression = "java(java.time.ZonedDateTime.now())")
    KitchenOrder toKitchenOrder(OrderToDishRequest orderToDishRequest);
}
