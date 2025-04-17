package ru.liga.restaurant.kitchen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.restaurant.kitchen.exception.InsufficientStockException;
import ru.liga.restaurant.kitchen.exception.NotFoundException;
import ru.liga.restaurant.kitchen.model.request.OrderRequest;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;
import ru.liga.restaurant.kitchen.service.KitchenService;

@RestController
@RequestMapping("/kitchen")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Kitchen контроллер", description = "API для управления заказами на кухне")
public class KitchenController {
    private final KitchenService kitchenService;

    @Operation(
            summary = "Принять заказ на кухню",
            description = "Принимает заказ от клиента для приготовления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно принят"),
                    @ApiResponse(responseCode = "409", description = "Недостаточно продуктов",
                            content = @Content(schema = @Schema(implementation = InsufficientStockException.class)))
            }
    )
    @PostMapping
    public OrderToDishResponse acceptOrder(@RequestBody OrderToDishRequest orderToDishRequest) {
        log.info("Принятие заказа на кухню: {}", orderToDishRequest);
        OrderToDishResponse orderToDishResponse = kitchenService.acceptOrder(orderToDishRequest);
        log.debug("Заказ успешно принят: {}", orderToDishResponse);
        return orderToDishResponse;
    }

    @Operation(
            summary = "Отклонить заказ",
            description = "Проверяет возможность создания заказа",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно отклонен"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundException.class))),
                    @ApiResponse(responseCode = "409", description = "Недостаточно продуктов",
                            content = @Content(schema = @Schema(implementation = InsufficientStockException.class)))
            }
    )
    @PostMapping("/reject")
    public void rejectOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Проверка на возможность создания заказа: {}", orderRequest);
        kitchenService.rejectOrder(orderRequest);
        log.debug("Создание заказа {} возможно", orderRequest);
    }

    @Operation(
            summary = "Изменить статус заказа на \"ГОТОВ\"",
            description = "Обновляет статус заказа на \"ГОТОВ\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус успешно обновлен"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundException.class)))
            }
    )
    @GetMapping("/{id}")
    public void readyOrder(@PathVariable Long id) {
        log.info("Начало изменения статуса заказа {} на \"ГОТОВ\"", id);
        kitchenService.readyOrder(id);
        log.debug("Статус заказа {} обновлен на \"ГОТОВ\"", id);
    }

    @Operation(
            summary = "Получить список заказов кухни",
            description = "Возвращает пагинированный список заказов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список успешно получен")
            }
    )
    @GetMapping
    public OrderToDishListResponse getKitchenList(
            @RequestParam Integer pageNumber, @RequestParam Integer size) {
        log.info("Получение списка заказов кухни Страница: {}, размер: {}", pageNumber, size);
        OrderToDishListResponse orderToDishListResponse = kitchenService.getKitchenList(pageNumber, size);
        log.debug("Получено {} заказов", orderToDishListResponse.getOrderToDishResponseForKitchenList().size());
        return orderToDishListResponse;
    }
}
