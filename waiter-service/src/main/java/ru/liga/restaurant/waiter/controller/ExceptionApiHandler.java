package ru.liga.restaurant.waiter.controller;

import feign.FeignException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.exception.OrderSerializationException;

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
     * Обрабатывает исключение {@link OrderSerializationException} (ошибка сериализации заказа)
     *
     * @param orderSerializationException перехваченное исключение
     * @return ответ с HTTP-статусом и сообщением исключения
     */
    @ExceptionHandler(OrderSerializationException.class)
    public ResponseStatusException orderSerializationException(
            OrderSerializationException orderSerializationException) {
        return new ResponseStatusException(
                orderSerializationException.getHttpStatus(), orderSerializationException.getMessage());
    }

    /**
     * Обрабатывает исключение {@link FeignException}
     * (исключение, возникшее при взаимодействии с другим сервисом через Feign Client)
     *
     * @param feignException перехваченное исключение
     * @return ответ с HTTP-статусом и сообщением исключения
     */
    @ExceptionHandler(FeignException.class)
    public ResponseStatusException feignException(FeignException feignException) {
        return new ResponseStatusException(HttpStatusCode.valueOf(
                feignException.status()), feignException.getMessage());
    }
}