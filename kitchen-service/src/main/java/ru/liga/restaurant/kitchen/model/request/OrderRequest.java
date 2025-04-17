package ru.liga.restaurant.kitchen.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос на создание заказа")
public class OrderRequest {
    @Schema(description = "ID официанта", example = "3")
    private Long waiterId;
    @Schema(description = "Номер стола", example = "A12")
    private String tableNo;
    @Schema(description = "Тип оплаты", example = "CASH", allowableValues = {"CASH", "CARD"})
    private String paymentType;
    @Schema(description = "Список позиций заказа")
    private List<OrderPositionsRequest> positions;
}