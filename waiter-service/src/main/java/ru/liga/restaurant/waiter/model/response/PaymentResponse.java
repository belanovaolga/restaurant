package ru.liga.restaurant.waiter.model.response;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PaymentResponse {
    private Long orderNo;
    private String paymentType;
    private ZonedDateTime paymentDate;
    private Double paymentSum;
}
