package ru.liga.restaurant.waiter.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;

/**
 * DTO ответа с информацией о статусе заказа
 */
@Data
@Builder
@Schema(description = "Информация о статусе заказа")
public class StatusResponse {
    @Schema(description = "Статус заказа", implementation = WaiterStatus.class)
    private WaiterStatus waiterStatus;
}