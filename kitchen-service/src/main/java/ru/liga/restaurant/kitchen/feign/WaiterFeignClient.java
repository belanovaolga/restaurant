package ru.liga.restaurant.kitchen.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "waiter", url = "${service.host.waiter}")
public interface WaiterFeignClient {
    @PostMapping("/{id}")
    void orderReady(@PathVariable Long id);
}