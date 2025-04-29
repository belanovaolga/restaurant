package ru.liga.restaurant.waiter.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.restaurant.waiter.grpc.OrderGrpcServiceImpl;
import ru.liga.restaurant.waiter.service.OrderService;

import java.io.IOException;

/**
 * Конфигурационный класс для настройки gRPC сервера
 */
@Configuration
public class GrpcServerConfig {

    /**
     * Создает и запускает экземпляр gRPC сервера
     *
     * @param orderService реализация сервиса заказов
     * @return настроенный gRPC сервер
     */
    @Bean
    public Server grpcServer(OrderService orderService) throws IOException {
        return ServerBuilder.forPort(9022)
                .addService(new OrderGrpcServiceImpl(orderService))
                .build()
                .start();
    }
}