package ru.liga.restaurant.kitchen.grpc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационные свойства для gRPC клиента
 */
@Configuration
@ConfigurationProperties(prefix = "grpc.client.order-service")
@Getter
@Setter
public class GrpcClientProperties {
    private String host;
    private int port;
}
