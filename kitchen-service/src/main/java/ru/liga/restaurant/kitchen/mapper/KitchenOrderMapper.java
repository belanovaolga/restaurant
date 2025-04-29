package ru.liga.restaurant.kitchen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.KitchenOrderResponse;

/**
 * Маппер для преобразования между сущностью KitchenOrder другими
 */
@Mapper(componentModel = "spring")
public interface KitchenOrderMapper {
    /**
     * Преобразует сущность KitchenOrder в KitchenOrderResponse DTO
     *
     * @param kitchenOrder сущность заказа на кухне
     * @return DTO с информацией о заказе
     */
    @Mapping(target = "kitchenStatus", source = "status")
    KitchenOrderResponse toKitchenOrderResponse(KitchenOrder kitchenOrder);

    /**
     * Создает новую сущность KitchenOrder из OrderToDishRequest
     * Устанавливает начальные значения:
     * - статус AWAITING
     * - текущую дату создания
     *
     * @param orderToDishRequest DTO с данными о заказе
     * @return сущность KitchenOrder
     */
    @Mapping(target = "status", expression = "java(ru.liga.restaurant.kitchen.model.enums.KitchenStatus.AWAITING)")
    @Mapping(target = "createDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "waiterOrderNo", source = "kitchenOrderId")
    KitchenOrder toKitchenOrder(OrderToDishRequest orderToDishRequest);
}