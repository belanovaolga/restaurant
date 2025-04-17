package ru.liga.restaurant.waiter.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.liga.restaurant.waiter.model.request.OrderRequest;

@FeignClient(value = "kitchen", url = "localhost:8080/kitchen")
public interface KitchenFeignClient {
    @PostMapping("/reject")
    void rejectOrder(@RequestBody OrderRequest orderRequest);
}
