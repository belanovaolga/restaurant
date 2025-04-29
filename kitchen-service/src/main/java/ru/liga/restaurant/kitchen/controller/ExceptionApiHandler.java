package ru.liga.restaurant.kitchen.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.kitchen.exception.GrpcException;
import ru.liga.restaurant.kitchen.exception.InsufficientStockException;
import ru.liga.restaurant.kitchen.exception.NotFoundException;
import ru.liga.restaurant.kitchen.exception.OrderCannotBeDeletedException;

/**
 * Глобальный обработчик исключений
 */
@RestControllerAdvice
public class ExceptionApiHandler {

    /**
     * Обрабатывает исключение {@link NotFoundException} (сущность не найдена)
     *
     * @param notFoundException перехваченное исключение
     * @return ответ с HTTP-статусом и сообщением исключения
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseStatusException notFoundException(NotFoundException notFoundException) {
        return new ResponseStatusException(notFoundException.getHttpStatus(), notFoundException.getMessage());
    }

    /**
     * Обрабатывает исключение {@link InsufficientStockException} (недостаточно продуктов)
     *
     * @param insufficientStockException перехваченное исключение
     * @return ответ с HTTP-статусом и сообщением исключения
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseStatusException insufficientStockException(InsufficientStockException insufficientStockException) {
        return new ResponseStatusException(insufficientStockException.getHttpStatus(),
                insufficientStockException.getMessage());
    }


    /**
     * Обрабатывает исключение {@link OrderCannotBeDeletedException} (невозможно удалить заказ)
     *
     * @param orderCannotBeDeletedException перехваченное исключение
     * @return ответ с HTTP-статусом и сообщением исключения
     */
    @ExceptionHandler(OrderCannotBeDeletedException.class)
    public ResponseStatusException orderCannotBeDeletedException(
            OrderCannotBeDeletedException orderCannotBeDeletedException) {
        return new ResponseStatusException(orderCannotBeDeletedException.getHttpStatus(),
                orderCannotBeDeletedException.getMessage());
    }

    /**
     * Обрабатывает исключение {@link GrpcException} (Ошибка при обращении к GRPC серверу)
     *
     * @param grpcException перехваченное исключение
     * @return ответ с HTTP-статусом и сообщением исключения
     */
    @ExceptionHandler(GrpcException.class)
    public ResponseStatusException grpcException(GrpcException grpcException) {
        return new ResponseStatusException(grpcException.getHttpStatus(), grpcException.getMessage());
    }
}
