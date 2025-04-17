package ru.liga.restaurant.kitchen.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dish {
    private Long dishId;
    private Long balance;
    private String shortName;
    private String dishComposition;
}