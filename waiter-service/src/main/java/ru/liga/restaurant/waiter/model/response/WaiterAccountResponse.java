package ru.liga.restaurant.waiter.model.response;

import lombok.Data;

@Data
public class WaiterAccountResponse {
    private Long waiterId;
    private String name;
    private String sex;
}
