package ru.liga.restaurant.waiter.model.request;

import lombok.Data;

@Data
public class OrderPositionsRequest {
    private Long dishNum;
    private Long menuPositionId;
}
