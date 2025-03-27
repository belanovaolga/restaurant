package ru.liga.restaurant.kitchen.dao;

import lombok.Data;

import java.util.List;

@Data
public class KitchenDao {
    private Long id;
    private List<String> dishes;
    private Long waiterId;
}
