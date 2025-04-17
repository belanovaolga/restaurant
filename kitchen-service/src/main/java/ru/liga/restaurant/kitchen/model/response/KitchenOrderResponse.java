package ru.liga.restaurant.kitchen.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.liga.restaurant.kitchen.model.enums.KitchenStatus;

import java.time.ZonedDateTime;

@Data
@Schema(description = "Информация о заказе на кухне")
public class KitchenOrderResponse {
    @Schema(description = "ID заказа на кухне", example = "78")
    private Long kitchenOrderId;
    @Schema(description = "Номер заказа у официанта", example = "45")
    private Long waiterOrderNo;
    @Schema(description = "Статус заказа на кухне", example = "IN_PROGRESS",
            allowableValues = {"AWAITING", "AT_WORK", "READY"})
    private KitchenStatus kitchenStatus;
    @Schema(description = "Дата и время создания заказа", example = "2023-05-15T14:30:45+03:00")
    private ZonedDateTime createDate;
}
