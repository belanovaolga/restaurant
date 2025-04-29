package ru.liga.restaurant.kitchen.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность, связывающая заказ и блюдо
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderToDish {
    /**
     * Заказ на кухне
     */
    private KitchenOrder kitchenOrder;
    /**
     * Блюдо из заказа
     */
    private Dish dish;
    /**
     * Количество порций блюда
     */
    private Long dishesNumber;
}