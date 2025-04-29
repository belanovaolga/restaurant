package ru.liga.restaurant.kitchen.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Feign клиент для взаимодействия с сервисом официантов (waiter-service)
 */
@FeignClient(value = "waiter", url = "${service.host.waiter}")
public interface WaiterFeignClient {
    /**
     * Уведомляет сервис официантов о готовности заказа
     *
     * @param id идентификатор заказа
     */
    @PostMapping("/{id}")
    void orderReady(@PathVariable Long id);
}