package ru.liga.restaurant.waiter.model.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long waiterId;
    private String tableNo;
    private String paymentType;
    private List<OrderPositionsRequest> positions;
}
