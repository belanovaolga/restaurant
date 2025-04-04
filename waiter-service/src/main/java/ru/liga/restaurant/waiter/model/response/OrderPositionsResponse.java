package ru.liga.restaurant.waiter.model.response;

import lombok.Data;

@Data
public class OrderPositionsResponse {
    private Long compositionId;
    private Long dishNum;
    private MenuResponse menuPositionId;
}
