package ru.liga.restaurant.waiter.dao;

import lombok.Data;

import java.util.List;

@Data
public class OrderDao {
    private Long id;
    private List<String> dishes;
    private Double sum;
    private Long waiterId;
}
