package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.waiter.model.entity.Payment;
import ru.liga.restaurant.waiter.model.entity.WaiterOrder;
import ru.liga.restaurant.waiter.model.response.PaymentResponse;

/**
 * Маппер для преобразований между сущностью Payment и другими
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper {
    /**
     * Преобразует Payment в PaymentResponse
     */
    PaymentResponse toPaymentResponse(Payment payment);
}