package ru.liga.restaurant.waiter.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Информация о заказе")
public class OrderResponse {
    @Schema(description = "Номер заказа", example = "45")
    private Long orderNo;
    @Schema(description = "Статус заказа", implementation = WaiterStatus.class)
    private WaiterStatus waiterStatus;
    @Schema(description = "Дата создания", example = "2023-05-15T14:30:45+03:00")
    private ZonedDateTime createDate;
    @Schema(description = "Информация об официанте")
    private WaiterAccountResponse waiterAccount;
    @Schema(description = "Номер стола", example = "A15")
    private String tableNo;
    @Schema(description = "Информация об оплате")
    private PaymentResponse payment;
    @Schema(description = "Список позиций заказа")
    private List<OrderPositionsResponse> positions;
}
