package ru.liga.restaurant.kitchen.client.grpc;

import io.grpc.stub.StreamObserver;
import kitchen.Kitchen;
import kitchen.KitchenServiceGrpc;
import lombok.RequiredArgsConstructor;
import ru.liga.restaurant.kitchen.model.request.DishRequest;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;
import ru.liga.restaurant.kitchen.service.KitchenServiceImpl;

import java.util.List;

@RequiredArgsConstructor
public class KitchenGrpcService extends KitchenServiceGrpc.KitchenServiceImplBase {
    private final KitchenServiceImpl kitchenService;

    @Override
    public void acceptOrder(
            Kitchen.OrderToDishRequest request,
            StreamObserver<Kitchen.OrderToDishResponse> responseObserver
    ) {
        List<DishRequest> dishRequestList = request.getDishRequestList().stream()
                .map(dishRequest -> {
                    return DishRequest.builder()
                            .dishId(dishRequest.getDishId())
                            .dishesNumber(dishRequest.getDishesNumber())
                            .build();
                })
                .toList();

        System.out.println("Сообщение пришло");

        OrderToDishRequest orderToDishRequest = OrderToDishRequest.builder()
                .kitchenOrderId(request.getKitchenOrderId())
                .waiterOrderNo(request.getWaiterOrderNo())
                .dishRequest(dishRequestList)
                .build();

        OrderToDishResponse orderToDishResponse = kitchenService.acceptOrder(orderToDishRequest);

        Kitchen.KitchenOrderResponse kitchenOrderResponse = Kitchen.KitchenOrderResponse.newBuilder()
                .setKitchenOrderId(orderToDishResponse.getKitchenOrderResponse().getKitchenOrderId())
                .setStatus(Kitchen.KitchenStatus.AWAITING)
                .build();

        List<Kitchen.DishResponse> dishResponseList = orderToDishResponse.getDishResponse().stream()
                .map(dishResponse -> {
                    return Kitchen.DishResponse.newBuilder()
                            .setDishId(dishResponse.getDishId())
                            .setShortName(dishResponse.getShortName())
                            .build();
                })
                .toList();

        System.out.println("Отправляем назад");

        Kitchen.OrderToDishResponse response = Kitchen.OrderToDishResponse.newBuilder()
                .setKitchenOrderResponse(kitchenOrderResponse)
                .addAllDishResponse(dishResponseList)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
