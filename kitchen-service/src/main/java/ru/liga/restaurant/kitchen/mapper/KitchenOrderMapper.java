package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.KitchenOrderResponse;

@Mapper(componentModel = "spring")
public interface KitchenOrderMapper {
    KitchenOrderResponse toKitchenOrderResponse(KitchenOrder kitchenOrder);

    @Mapping(target = "status", expression = "java(ru.liga.restaurant.kitchen.model.enums.KitchenStatus.AWAITING)")
    @Mapping(target = "createDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "waiterOrderNo", source = "orderToDishRequest.kitchenOrderId")
    KitchenOrder toKitchenOrder(OrderToDishRequest orderToDishRequest);
}