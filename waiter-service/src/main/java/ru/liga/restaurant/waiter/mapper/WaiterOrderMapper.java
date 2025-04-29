package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.waiter.model.entity.WaiterAccount;
import ru.liga.restaurant.waiter.model.entity.WaiterOrder;
import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;

/**
 * Основной маппер для работы с заказами
 */
@Mapper(componentModel = "spring",
        uses = {PaymentMapper.class, WaiterAccountMapper.class, OrderPositionsMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WaiterOrderMapper {
    /**
     * Преобразует WaiterOrder в OrderResponse
     */
    @Mapping(target = "waiterStatus", source = "status")
    OrderResponse toOrderResponse(WaiterOrder waiterOrder);

    /**
     * Создает WaiterOrder из запроса
     */
    @Mapping(target = "createDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "orderNo", ignore = true)
    @Mapping(target = "payment", expression = "java(ru.liga.restaurant.waiter.model.entity.Payment.builder().build())")
    @Mapping(target = "positions", ignore = true)
    @Mapping(target = "status", expression = "java(ru.liga.restaurant.waiter.model.enums.WaiterStatus.ACCEPT)")
    WaiterOrder toWaiterOrder(OrderRequest orderRequest, WaiterAccount waiterAccount);
}
