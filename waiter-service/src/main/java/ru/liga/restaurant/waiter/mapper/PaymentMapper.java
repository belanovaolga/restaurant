package ru.liga.restaurant.waiter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.liga.restaurant.waiter.model.Payment;
import ru.liga.restaurant.waiter.model.WaiterOrder;
import ru.liga.restaurant.waiter.model.response.PaymentResponse;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "paymentDate", expression = "java(java.time.ZonedDateTime.now())")
    Payment toPayment(WaiterOrder waiterOrder, String paymentType, Double paymentSum);

    PaymentResponse toPaymentResponse(Payment payment);
}
