package ru.liga.restaurant.kitchen.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "waiter", url = "${app.feign.waiter.url}")
public interface WaiterFeignClient {
    @PutMapping("/{id}")
    void orderReady(@PathVariable Long id);
}
