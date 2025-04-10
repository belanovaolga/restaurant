package ru.liga.restaurant.waiter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.restaurant.waiter.client.KitchenClient;
import ru.liga.restaurant.waiter.client.feign.KitchenFeignClient;
import ru.liga.restaurant.waiter.client.grpc.KitchenGrpcClient;

@Configuration
public class CommunicationConfig {

    @Bean
    @ConditionalOnProperty(name = "app.communication.mode", havingValue = "feign")
    public KitchenClient feignKitchenClient(KitchenFeignClient feignClient) {
        return feignClient;
    }

    @Bean
    @ConditionalOnProperty(name = "app.communication.mode", havingValue = "grpc")
    public KitchenClient grpcKitchenClient(
            @Value("${app.grpc.kitchen.host}") String host,
            @Value("${app.grpc.kitchen.port}") int port
    ) {
        return new KitchenGrpcClient(host, port);
    }
}
