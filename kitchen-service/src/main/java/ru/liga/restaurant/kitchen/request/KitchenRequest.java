package ru.liga.restaurant.kitchen.request;

import lombok.Data;

import java.util.List;

@Data
public class KitchenRequest {
    private Long id;
    private List<String> dishes;
    private Long waiterId;
}
