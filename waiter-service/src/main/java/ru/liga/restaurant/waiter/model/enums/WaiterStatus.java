package ru.liga.restaurant.waiter.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Перечисление статусов заказа у официантов
 */
@Schema(description = "Статус заказа у официанта", enumAsRef = true)
public enum WaiterStatus {
    @Schema(description = "Заказ принят", example = "ACCEPT")
    ACCEPT,

    @Schema(description = "Заказ готовится", example = "PREPARE")
    PREPARE,

    @Schema(description = "Заказ готов", example = "READY")
    READY,

    @Schema(description = "Заказ удален", example = "DELETED")
    DELETED
}