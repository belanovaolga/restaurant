package ru.liga.restaurant.waiter.service;

import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;
import ru.liga.restaurant.waiter.model.response.OrderResponseList;
import ru.liga.restaurant.waiter.model.response.StatusResponse;

/**
 * Интерфейс сервиса для работы с заказами
 */
public interface OrderService {
    /**
     * Получает список заказов с пагинацией
     */
    OrderResponseList getOrderList(Integer pageNumber, Integer size);

    /**
     * Получает заказ по идентификатору
     */
    OrderResponse getOrder(Long id);

    /**
     * Создает новый заказ
     */
    OrderResponse createOrder(OrderRequest orderRequest);

    /**
     * Возвращает статус заказа
     */
    StatusResponse getStatus(Long id);

    /**
     * Изменяет статус заказа на "ГОТОВ"
     */
    void orderReady(Long id);

    /**
     * Изменяет статус заказа на "УДАЛЕН"
     */
    void rejectOrder(Long id);
}