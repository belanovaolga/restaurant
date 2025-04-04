package ru.liga.restaurant.waiter.model.response;

import lombok.Data;
import ru.liga.restaurant.waiter.model.enums.Status;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderNo;
    private Status status;
    private ZonedDateTime createDate;
    private WaiterAccountResponse waiterId;
    private String tableNo;
    private PaymentResponse payment;
    private List<OrderPositionsResponse> positions;
}
