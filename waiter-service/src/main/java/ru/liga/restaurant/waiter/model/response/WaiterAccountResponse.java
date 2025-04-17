package ru.liga.restaurant.waiter.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация об официанте")
public class WaiterAccountResponse {
    @Schema(description = "ID официанта", example = "3")
    private Long waiterId;
    @Schema(description = "Имя официанта", example = "Иван")
    private String name;
    @Schema(description = "Пол официанта", example = "MALE", allowableValues = {"MALE", "FEMALE"})
    private String sex;
}
