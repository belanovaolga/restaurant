package ru.liga.restaurant.waiter.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DishRequest {
    private Long dishId;
    private Long dishesNumber;
}
