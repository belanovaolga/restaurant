package ru.liga.restaurant.kitchen.grpc;

import ru.liga.restaurant.kitchen.exception.GrpcException;

/**
 * Сервис для взаимодействия с waiter-service через gRPC
 */
public interface GrpcService {
    /**
     * Отправляет запрос на отклонение заказа
     *
     * @param waiterOrderNo идентификатор заказа
     * @throws GrpcException если сервер вернул ошибку
     */
    void rejectOrder(Long waiterOrderNo);
}