package ru.liga.restaurant.kitchen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.restaurant.kitchen.custom.mapper.DishCustomMapper;
import ru.liga.restaurant.kitchen.custom.mapper.KitchenOrderCustomMapper;
import ru.liga.restaurant.kitchen.custom.mapper.OrderToDishCustomMapper;
import ru.liga.restaurant.kitchen.exception.InsufficientStockException;
import ru.liga.restaurant.kitchen.exception.OrderNotFoundException;
import ru.liga.restaurant.kitchen.feign.WaiterFeignClient;
import ru.liga.restaurant.kitchen.mapper.DishMapper;
import ru.liga.restaurant.kitchen.mapper.KitchenOrderMapper;
import ru.liga.restaurant.kitchen.mapper.OrderToDishMapper;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;
import ru.liga.restaurant.kitchen.model.enums.KitchenStatus;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.DishResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
    private final DishCustomMapper dishCustomMapper;
    private final KitchenOrderCustomMapper kitchenOrderCustomMapper;
    private final OrderToDishCustomMapper orderToDishCustomMapper;
    private final DishMapper dishMapper;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderToDishMapper orderToDishMapper;
    private final WaiterFeignClient waiterFeignClient;

    @Override
    @Transactional
    public OrderToDishResponse acceptOrder(OrderToDishRequest orderToDishRequest) {
        List<DishResponse> dishResponseList = orderToDishRequest.getDishRequest().stream()
                .map(dishRequest -> {
                    Dish dish = findDishById(dishRequest.getDishId());

                    Long currentBalance = dish.getBalance();
                    Long balance = dishRequest.getDishesNumber();

                    if (currentBalance < balance) {
                        throw InsufficientStockException.builder()
                                .message("Недостаточно продуктов для блюда " + dish.getShortName())
                                .httpStatus(HttpStatus.CONFLICT)
                                .build();
                    }
                    dish.setBalance(currentBalance - balance);
                    dishCustomMapper.update(dish);

                    OrderToDish orderToDish = orderToDishMapper.toOrderToDish(orderToDishRequest, dishRequest);
                    orderToDishCustomMapper.insert(orderToDish);

                    return dishMapper.toDishResponse(dish);
                })
                .toList();

        KitchenOrder kitchenOrder = kitchenOrderMapper.toKitchenOrder(orderToDishRequest);
        kitchenOrderCustomMapper.insert(kitchenOrder);

        return orderToDishMapper.toOrderToDishResponse(kitchenOrderMapper.toKitchenOrderResponse(kitchenOrder), dishResponseList);
    }

    @Override
    @Transactional
    public String rejectOrder(OrderToDishRequest orderToDishRequest) {
        orderToDishRequest.getDishRequest()
                .forEach(dishRequest -> {
                    Dish dish = findDishById(dishRequest.getDishId());

                    if (dish.getBalance() < dishRequest.getDishesNumber()) {
                        throw InsufficientStockException.builder()
                                .message("Заказ " + orderToDishRequest.getKitchenOrderId() + " отклонен. Недостаточно продуктов.")
                                .httpStatus(HttpStatus.CONFLICT)
                                .build();
                    }
                });

        acceptOrder(orderToDishRequest);
        return "Заказ " + orderToDishRequest.getKitchenOrderId() + " принят";
    }

    @Override
    public void readyOrder(Long id) {
        KitchenOrder kitchenOrder = findKitchenOrderById(id);
        kitchenOrder.setKitchenStatus(KitchenStatus.READY);
        kitchenOrderCustomMapper.updateStatus(kitchenOrder);

        waiterFeignClient.orderReady(id);
    }

    @Override
    public OrderToDishListResponse getKitchenList() {
        return null;

//        List<OrderToDish> orderToDishCustomMapperAll = orderToDishCustomMapper.findAll();
//        List<OrderToDishResponse> orderToDishResponseList = orderToDishCustomMapperAll.stream()
//                .map(orderToDish -> {
//                    KitchenOrder kitchenOrder = findKitchenOrderById(orderToDish.getKitchenOrderId());
//                    Dish dish = findDishById(orderToDish.getDishId());
//                    return orderToDishMapper
//                            .toOrderToDishResponse(kitchenOrderMapper.toKitchenOrderResponse(kitchenOrder), dishMapper.toDishResponse(dish));
//                })
//                .toList();
//
//        return OrderToDishListResponse.builder().orderToDishResponseList(orderToDishResponseList).build();
    }


    private Dish findDishById(Long dishId) {
        return dishCustomMapper.findById(dishId)
                .orElseThrow(() -> OrderNotFoundException.builder().message("Блюда " + dishId + " не существует")
                        .httpStatus(HttpStatus.NOT_FOUND).build());
    }

    private KitchenOrder findKitchenOrderById(Long kitchenOrderId) {
        return kitchenOrderCustomMapper.findById(kitchenOrderId)
                .orElseThrow(() -> OrderNotFoundException.builder().message("Заказа " + kitchenOrderId + " не существует")
                        .httpStatus(HttpStatus.NOT_FOUND).build());
    }
}
