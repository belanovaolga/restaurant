package ru.liga.restaurant.kitchen.service;

import ru.liga.restaurant.kitchen.model.request.OrderRequest;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;

/**
 * Интерфейс сервиса для работы с заказами на кухне
 */
public interface KitchenService {
    /**
     * Принимает новый заказ на кухню
     *
     * @param orderToDishRequest DTO с данными о заказе
     * @return DTO с информацией о созданном заказе
     */
    OrderToDishResponse acceptOrder(OrderToDishRequest orderToDishRequest);

    /**
     * Отклоняет(удаляет) заказ по идентификатору
     *
     * @param id идентификатор заказа
     */
    void rejectOrder(Long id);

    /**
     * Проверяет возможность создания заказа
     *
     * @param orderRequest DTO с информацией о заказе
     */
    void checkOrder(OrderRequest orderRequest);

    /**
     * Изменяет статус заказа на "ГОТОВ"
     *
     * @param id идентификатор заказа
     */
    void readyOrder(Long id);

    /**
     * Возвращает список заказов с пагинацией
     *
     * @param pageNumber номер страницы
     * @param size       количество элементов на странице
     * @return пагинированный список заказов
     */
    OrderToDishListResponse getKitchenList(Integer pageNumber, Integer size);
}
