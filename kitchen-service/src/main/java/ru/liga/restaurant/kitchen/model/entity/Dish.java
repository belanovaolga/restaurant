package ru.liga.restaurant.kitchen.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность блюда
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    /**
     * Уникальный идентификатор блюда
     */
    private Long dishId;
    /**
     * Количество(остаток) блюд
     */
    private Long balance;
    /**
     * Название блюда
     */
    private String shortName;
    /**
     * Состав блюда
     */
    private String dishComposition;
}