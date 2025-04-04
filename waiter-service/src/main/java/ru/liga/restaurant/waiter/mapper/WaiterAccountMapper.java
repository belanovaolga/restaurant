package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import ru.liga.restaurant.waiter.model.WaiterAccount;
import ru.liga.restaurant.waiter.model.response.WaiterAccountResponse;

@Mapper(componentModel = "spring")
public interface WaiterAccountMapper {
    WaiterAccountResponse toWaiterAccountResponse(WaiterAccount waiterAccount);
}
