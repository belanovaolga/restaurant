package ru.liga.restaurant.kitchen.model.response;

import lombok.Data;
import ru.liga.restaurant.kitchen.model.enums.Status;

import java.time.ZonedDateTime;

@Data
public class KitchenOrderResponse {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private Status status;
    private ZonedDateTime createDate;
}
