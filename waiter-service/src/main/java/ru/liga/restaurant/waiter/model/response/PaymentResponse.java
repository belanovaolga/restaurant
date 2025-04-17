package ru.liga.restaurant.waiter.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Schema(description = "Информация об оплате")
public class PaymentResponse {
    @Schema(description = "Номер заказа", example = "45")
    private Long orderNo;
    @Schema(description = "Тип оплаты", example = "CARD", allowableValues = {"CASH", "CARD"})
    private String paymentType;
    @Schema(description = "Дата оплаты", example = "2023-05-15T15:45:00+03:00")
    private ZonedDateTime paymentDate;
    @Schema(description = "Сумма оплаты", example = "1250.50")
    private Double paymentSum;
}
