package ru.liga.restaurant.waiter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.restaurant.waiter.model.request.OrderRequest;

/**
 * Feign клиент для взаимодействия с сервисом кухни (kitchen-service)
 */
@FeignClient(value = "kitchen", url = "localhost:8080/kitchen")
public interface KitchenFeignClient {
    /**
     * Отправляет запрос на проверку возможности создания заказа
     *
     * @param orderRequest DTO с данными о заказе
     */
    @PostMapping("/check")
    void checkOrder(@RequestBody OrderRequest orderRequest);
}