package ru.liga.restaurant.waiter.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private List<String> dishes;
    private Double sum;
    private Long waiterId;
    private Status status;
}
