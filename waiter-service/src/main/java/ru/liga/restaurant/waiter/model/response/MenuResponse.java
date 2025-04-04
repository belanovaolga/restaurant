package ru.liga.restaurant.waiter.model.response;

import lombok.Data;

@Data
public class MenuResponse {
    private Long id;
    private String dishName;
    private Double dishCost;
}
