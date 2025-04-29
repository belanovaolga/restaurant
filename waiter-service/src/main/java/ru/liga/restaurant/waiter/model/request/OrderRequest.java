package ru.liga.restaurant.waiter.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO запроса на создание нового заказа
 */
@Data
@Builder
@Schema(description = "Запрос на создание заказа")
public class OrderRequest {
    @Schema(description = "ID официанта", example = "3")
    private Long waiterId;

    @Schema(description = "Номер стола", example = "A15")
    private String tableNo;

    @Schema(description = "Тип оплаты", example = "CARD", allowableValues = {"CASH", "CARD"})
    private String paymentType;

    @Schema(description = "Список позиций заказа")
    private List<OrderPositionsRequest> positions;
}