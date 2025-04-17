package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.waiter.model.WaiterAccount;
import ru.liga.restaurant.waiter.model.WaiterOrder;
import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;

@Mapper(componentModel = "spring",
        uses = {PaymentMapper.class, WaiterAccountMapper.class, OrderPositionsMapper.class, MenuMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WaiterOrderMapper {
    @Mapping(target = "waiterStatus", source = "status")
    OrderResponse toOrderResponse(WaiterOrder waiterOrder);

    @Mapping(target = "createDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "orderNo", ignore = true)
    @Mapping(target = "payment", expression = "java(ru.liga.restaurant.waiter.model.Payment.builder().build())")
    @Mapping(target = "positions", ignore = true)
    @Mapping(target = "status", expression = "java(ru.liga.restaurant.waiter.model.enums.WaiterStatus.ACCEPT)")
    WaiterOrder toWaiterOrder(OrderRequest orderRequest, WaiterAccount waiterAccount);

    @Mapping(target = "paymentType", source = "payment.paymentType")
    @Mapping(target = "waiterId", source = "waiterAccount.waiterId")
    OrderRequest toOrderRequest(WaiterOrder waiterOrder);
}
