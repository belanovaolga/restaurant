package ru.liga.restaurant.waiter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;
import ru.liga.restaurant.waiter.model.response.OrderToDishResponse;

@FeignClient(value = "kitchen", url = "${service.host.kitchen}")
public interface KitchenFeignClient {
    @PostMapping
    OrderToDishResponse acceptOrder(@RequestBody OrderToDishRequest orderToDishRequest);
}
