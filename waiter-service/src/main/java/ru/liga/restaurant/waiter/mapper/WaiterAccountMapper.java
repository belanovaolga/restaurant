package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import ru.liga.restaurant.waiter.model.entity.WaiterAccount;
import ru.liga.restaurant.waiter.model.response.WaiterAccountResponse;

/**
 * Маппер для преобразований между сущностью WaiterAccount и другими
 */
@Mapper(componentModel = "spring")
public interface WaiterAccountMapper {
    /**
     * Преобразует WaiterAccount в WaiterAccountResponse
     */
    WaiterAccountResponse toWaiterAccountResponse(WaiterAccount waiterAccount);
}
