package ru.liga.restaurant.kitchen.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderToDishListResponse {
    private List<OrderToDishResponse> orderToDishResponseList;
}
