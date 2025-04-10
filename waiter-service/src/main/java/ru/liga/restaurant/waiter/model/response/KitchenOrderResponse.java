package ru.liga.restaurant.waiter.model.response;

import lombok.Data;
import ru.liga.restaurant.waiter.model.enums.KitchenStatus;

import java.time.ZonedDateTime;

@Data
public class KitchenOrderResponse {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private KitchenStatus status;
    private ZonedDateTime createDate;
}