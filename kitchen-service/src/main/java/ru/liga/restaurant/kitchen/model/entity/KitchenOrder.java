package ru.liga.restaurant.kitchen.model.entity;

import lombok.Data;
import ru.liga.restaurant.kitchen.model.enums.Status;

import java.time.ZonedDateTime;

@Data
public class KitchenOrder {
    private Long kitchenOrderId;
    private Long waiterOrderNo;
    private Status status;
    private ZonedDateTime createDate;
}