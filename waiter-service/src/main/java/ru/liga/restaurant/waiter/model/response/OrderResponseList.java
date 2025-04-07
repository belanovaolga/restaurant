package ru.liga.restaurant.waiter.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponseList {
    private List<OrderResponse> orderResponseList;
}
