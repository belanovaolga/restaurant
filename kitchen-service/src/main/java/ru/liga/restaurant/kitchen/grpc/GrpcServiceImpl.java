package ru.liga.restaurant.kitchen.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import proto.OrderIdProto;
import proto.OrderServiceGrpc;
import ru.liga.restaurant.kitchen.exception.GrpcException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * Реализация gRPC клиента для взаимодействия с waiter-service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GrpcServiceImpl implements GrpcService {
    private ManagedChannel channel;
    private OrderServiceGrpc.OrderServiceBlockingStub stub;
    private final GrpcClientProperties grpcClientProperties;

    /**
     * Инициализирует gRPC канал
     */
    @PostConstruct
    public void init() {
        this.channel = ManagedChannelBuilder.forAddress(grpcClientProperties.getHost(), grpcClientProperties.getPort())
                .usePlaintext()
                .build();

        this.stub = OrderServiceGrpc.newBlockingStub(channel);
    }

    /**
     * Отправляет запрос на отклонение заказа
     *
     * @param waiterOrderNo идентификатор заказа
     * @throws GrpcException если сервер вернул ошибку
     */
    @Override
    public void rejectOrder(Long waiterOrderNo) {
        log.info("Отправка GRPC запроса на отмену заказа {}", waiterOrderNo);

        OrderIdProto.OrderIdProtoBuf request = OrderIdProto.OrderIdProtoBuf.newBuilder()
                .setOrderId(waiterOrderNo)
                .build();

        OrderIdProto.VoidResponse response = stub.rejectOrder(request);
        log.info("Получен ответ от GRPC сервера для заказа {}", waiterOrderNo);

        if (response.getHttpStatus() != 200) {
            log.warn("Ответ от GRPC сервера для заказа {} содержит исключение", waiterOrderNo);
            throw GrpcException.builder()
                    .message(response.getErrorMessage())
                    .httpStatus(HttpStatus.valueOf(response.getHttpStatus()))
                    .build();
        }
    }

    /**
     * Завершает работу gRPC канала
     */
    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            try {
                if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                    channel.shutdownNow();
                }
            } catch (InterruptedException e) {
                channel.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}