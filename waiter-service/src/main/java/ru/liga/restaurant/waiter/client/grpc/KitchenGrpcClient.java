package ru.liga.restaurant.waiter.client.grpc;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannelBuilder;
import kitchen.Kitchen;
import kitchen.KitchenServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import ru.liga.restaurant.waiter.client.KitchenClient;
import ru.liga.restaurant.waiter.model.enums.KitchenStatus;
import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;
import ru.liga.restaurant.waiter.model.response.DishResponse;
import ru.liga.restaurant.waiter.model.response.KitchenOrderResponse;
import ru.liga.restaurant.waiter.model.response.OrderToDishResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class KitchenGrpcClient implements KitchenClient {
    private final KitchenServiceGrpc.KitchenServiceBlockingStub stub;

    public KitchenGrpcClient(@Value("${grpc.kitchen.host}") String host,
                             @Value("${grpc.kitchen.port}") int port) {
        this.stub = KitchenServiceGrpc.newBlockingStub(
                ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()
        );
    }

    @Override
    public OrderToDishResponse acceptOrder(OrderToDishRequest request) {
        Kitchen.OrderToDishRequest grpcRequest = convertToGrpcRequest(request);
        Kitchen.OrderToDishResponse grpcResponse = stub.acceptOrder(grpcRequest);

        return convertFromGrpcResponse(grpcResponse);
    }

    private Kitchen.OrderToDishRequest convertToGrpcRequest(OrderToDishRequest request) {
        return Kitchen.OrderToDishRequest.newBuilder()
                .setKitchenOrderId(request.getKitchenOrderId())
                .setWaiterOrderNo(request.getWaiterOrderNo())
                .addAllDishRequest(request.getDishRequest().stream()
                        .map(dish -> Kitchen.DishRequest.newBuilder()
                                .setDishId(dish.getDishId())
                                .setDishesNumber(dish.getDishesNumber())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderToDishResponse convertFromGrpcResponse(Kitchen.OrderToDishResponse grpcResponse) {
        return new OrderToDishResponse(
                convertKitchenOrderResponse(grpcResponse.getKitchenOrderResponse()),
                grpcResponse.getDishResponseList().stream()
                        .map(dish -> new DishResponse(dish.getDishId(), dish.getShortName()))
                        .collect(Collectors.toList())
        );
    }

    private KitchenOrderResponse convertKitchenOrderResponse(Kitchen.KitchenOrderResponse grpcResponse) {
        return new KitchenOrderResponse(
                grpcResponse.getKitchenOrderId(),
                grpcResponse.getWaiterOrderNo(),
                KitchenStatus.valueOf(grpcResponse.getStatus().name()),
                convertTimestamp(grpcResponse.getCreateDate())
        );
    }

    private ZonedDateTime convertTimestamp(Timestamp timestamp) {
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneId.systemDefault());
    }
}
