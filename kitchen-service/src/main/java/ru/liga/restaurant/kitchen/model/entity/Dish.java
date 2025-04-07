package ru.liga.restaurant.kitchen.model.entity;

import lombok.Data;

@Data
public class Dish {
    private Long dishId;
    private Long balance;
    private String shortName;
    private String dishComposition;
}