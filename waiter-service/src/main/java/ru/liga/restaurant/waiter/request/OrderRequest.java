package ru.liga.restaurant.waiter.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long id;
    private List<String> dishes;
    private Double sum;
    private Long waiterId;
}
