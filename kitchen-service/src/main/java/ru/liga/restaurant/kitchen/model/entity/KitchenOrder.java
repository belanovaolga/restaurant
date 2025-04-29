package ru.liga.restaurant.kitchen.model.entity;

import lombok.Data;
import ru.liga.restaurant.kitchen.model.enums.KitchenStatus;

import java.time.ZonedDateTime;

/**
 * Сущность заказа на кухне
 */
@Data
public class KitchenOrder {
    /**
     * Уникальный идентификатор заказа
     */
    private Long kitchenOrderId;
    /**
     * Идентификатор заказа у официантов
     */
    private Long waiterOrderNo;
    /**
     * Текущий статус заказа
     */
    private KitchenStatus status;
    /**
     * Дата и время создания заказа
     */
    private ZonedDateTime createDate;
}