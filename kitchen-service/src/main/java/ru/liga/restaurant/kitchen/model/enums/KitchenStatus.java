package ru.liga.restaurant.kitchen.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус заказа на кухне", enumAsRef = true)
public enum KitchenStatus {
    @Schema(description = "Ожидает принятия в работу", example = "AWAITING")
    AWAITING,
    @Schema(description = "В процессе приготовления", example = "AT_WORK")
    AT_WORK,
    @Schema(description = "Готов к подаче", example = "READY")
    READY
}