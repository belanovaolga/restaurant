package ru.liga.restaurant.waiter.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO ответа со списком заказов
 */
@Data
@Builder
@Schema(description = "Список заказов")
public class OrderResponseList {
    @Schema(description = "Список заказов")
    private List<OrderResponse> orderResponsesList;
}
