package ru.liga.restaurant.waiter.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.restaurant.waiter.client.KitchenClient;
import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;
import ru.liga.restaurant.waiter.model.response.OrderToDishResponse;

@FeignClient(value = "kitchen", url = "${app.feign.kitchen.url}")
public interface KitchenFeignClient extends KitchenClient {
    @PostMapping
    OrderToDishResponse acceptOrder(@RequestBody OrderToDishRequest orderToDishRequest);
}
