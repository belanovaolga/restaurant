package ru.liga.restaurant.kitchen.model.entity;

import lombok.Data;
import ru.liga.restaurant.kitchen.model.enums.KitchenStatus;

import java.time.ZonedDateTime;

@Data
public class KitchenOrder {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private KitchenStatus status;
    private ZonedDateTime createDate;
}