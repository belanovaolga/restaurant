package ru.liga.restaurant.kitchen.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KitchenDto {
    private Long id;
    private List<String> dishes;
    private Long waiterId;
    private Status status;
}
