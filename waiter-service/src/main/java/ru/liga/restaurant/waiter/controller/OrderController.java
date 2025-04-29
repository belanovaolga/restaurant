package ru.liga.restaurant.waiter.controller;

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
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.exception.OrderSerializationException;
import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;
import ru.liga.restaurant.waiter.model.response.OrderResponseList;
import ru.liga.restaurant.waiter.model.response.StatusResponse;
import ru.liga.restaurant.waiter.service.OrderService;

/**
 * Контроллер для управления заказами официантами
 * Обеспечивает API для создания, получения, изменения статуса заказов
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Order контроллер", description = "API для управления заказами официантами")
public class OrderController {
    private final OrderService orderService;

    /**
     * Возвращает пагинированный список всех заказов
     *
     * @param pageNumber номер страницы
     * @param size       количество элементов на странице
     * @return список заказов с пагинацией
     */
    @Operation(
            summary = "Получить список заказов",
            description = "Возвращает пагинированный список всех заказов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список заказов успешно получен")
            }
    )
    @GetMapping
    public OrderResponseList getOrderList(
            @RequestParam Integer pageNumber, @RequestParam Integer size) {
        log.info("Получение списка заказов. Страница: {}, размер: {}", pageNumber, size);
        OrderResponseList orderResponseList = orderService.getOrderList(pageNumber, size);
        log.debug("Получено {} заказов", orderResponseList.getOrderResponsesList().size());
        return orderResponseList;
    }

    /**
     * Возвращает заказ по идентификатору
     *
     * @param id идентификатор заказа
     * @return ответ с заказом по идентификатору
     */
    @Operation(
            summary = "Получить заказ по ID",
            description = "Возвращает информацию о конкретном заказе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно найден"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundException.class)))
            }
    )
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        log.info("Получение заказа по ID: {}", id);
        OrderResponse orderResponse = orderService.getOrder(id);
        log.debug("Получен заказ: {}", orderResponse);
        return orderResponse;
    }

    /**
     * Создает новый заказ
     *
     * @param orderRequest запрос с данными заказа
     * @return ответ с созданным заказом
     */
    @Operation(
            summary = "Создать новый заказ",
            description = "Создает новый заказ на основе входных данных",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно создан"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundException.class))),
                    @ApiResponse(responseCode = "422", description = "Ошибка сериализации",
                            content = @Content(schema = @Schema(implementation = OrderSerializationException.class)))
            }
    )
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Начало создания нового заказа: {}", orderRequest);
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        log.info("Создан новый заказ с ID: {}", orderResponse.getOrderNo());
        return orderResponse;
    }

    /**
     * Возвращает статус заказа по идентификатору
     *
     * @param id идентификатор заказа
     * @return ответ со статусом заказа
     */
    @Operation(
            summary = "Получить статус заказа",
            description = "Возвращает текущий статус заказа по его ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус заказа получен"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundException.class)))
            }
    )
    @GetMapping("/status/{id}")
    public StatusResponse getStatus(@PathVariable Long id) {
        log.info("начало обработки запроса на получение статуса заказа с ID: {}", id);
        StatusResponse statusResponse = orderService.getStatus(id);
        log.debug("Статус заказа {}: {}", id, statusResponse);
        return statusResponse;
    }

    /**
     * Изменяет статус заказа на "ГОТОВ"
     *
     * @param id идентификатор заказа
     */
    @Operation(
            summary = "Изменить статус заказа на \"ГОТОВ\"",
            description = "Обновляет статус заказа на \"ГОТОВ\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус успешно обновлен"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundException.class)))
            }
    )
    @PostMapping("/{id}")
    void orderReady(@PathVariable Long id) {
        log.info("Начало изменения статуса заказа {} на \"ГОТОВ\"", id);
        orderService.orderReady(id);
        log.info("Статус заказа {} обновлен на \"ГОТОВ\"", id);
    }

    /**
     * Изменяет статус заказа на "УДАЛЕН"
     *
     * @param id идентификатор заказа
     */
    @Operation(
            summary = "Изменить статус заказа на \"УДАЛЕН\"",
            description = "Обновляет статус заказа на \"УДАЛЕН\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус успешно помечен как удаленный"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден",
                            content = @Content(schema = @Schema(implementation = NotFoundException.class)))
            }
    )
    @PostMapping("/{id}/reject")
    void rejectOrder(@PathVariable Long id) {
        log.info("Начало изменения статуса заказа {} на \"УДАЛЕН\"", id);
        orderService.rejectOrder(id);
        log.info("Статус заказа {} обновлен на \"УДАЛЕН\"", id);
    }
}
