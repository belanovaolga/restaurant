package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.waiter.model.WaiterAccount;
import ru.liga.restaurant.waiter.model.WaiterOrder;
import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;

@Mapper(componentModel = "spring",
        uses = {PaymentMapper.class, WaiterAccountMapper.class, OrderPositionsMapper.class, MenuMapper.class})
public interface WaiterOrderMapper {
    OrderResponse toOrderResponse(WaiterOrder waiterOrder);

    @Mapping(target = "createDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "waiterId", source = "waiterAccount")
    @Mapping(target = "orderNo", ignore = true)
    @Mapping(target = "payment", expression = "java(ru.liga.restaurant.waiter.model.Payment.builder().build())")
    @Mapping(target = "positions", ignore = true)
    @Mapping(target = "status", expression = "java(ru.liga.restaurant.waiter.model.enums.Status.ACCEPT)")
    WaiterOrder toWaiterOrder(OrderRequest orderRequest, WaiterAccount waiterAccount);
}
