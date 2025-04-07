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
import ru.liga.restaurant.kitchen.exception.OrderNotReadyException;
import ru.liga.restaurant.kitchen.mapper.DishMapper;
import ru.liga.restaurant.kitchen.mapper.KitchenOrderMapper;
import ru.liga.restaurant.kitchen.mapper.OrderToDishMapper;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;
import ru.liga.restaurant.kitchen.model.enums.Status;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
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

    @Override
    @Transactional
    public OrderToDishResponse acceptOrder(OrderToDishRequest orderToDishRequest) {
        Dish dish = findDishById(orderToDishRequest.getDishId());

        Long currentBalance = dish.getBalance();
        Long balance = orderToDishRequest.getDishesNumber();
        if (currentBalance < balance) {
            throw InsufficientStockException.builder()
                    .message("Недостаточно продуктов для блюда " + dish.getShortName()).httpStatus(HttpStatus.CONFLICT).build();
        }
        dish.setBalance(currentBalance - balance);
        dishCustomMapper.update(dish);

        KitchenOrder kitchenOrder = kitchenOrderMapper.toKitchenOrder(orderToDishRequest);
        kitchenOrderCustomMapper.insert(kitchenOrder);

        OrderToDish orderToDish = orderToDishMapper.toOrderToDish(orderToDishRequest);
        orderToDishCustomMapper.insert(orderToDish);

        return orderToDishMapper.toOrderToDishResponse(kitchenOrderMapper.toKitchenOrderResponse(kitchenOrder), dishMapper.toDishResponse(dish));
    }

    @Override
    @Transactional
    public String rejectOrder(OrderToDishRequest orderToDishRequest) {
        Dish dish = findDishById(orderToDishRequest.getDishId());

        if (dish.getBalance() < orderToDishRequest.getDishesNumber()) {
            return "Заказ " + orderToDishRequest.getKitchenOrderId() + " отклонен. Недостаточно продуктов.";
        }

        acceptOrder(orderToDishRequest);
        return "Заказ " + orderToDishRequest.getKitchenOrderId() + " принят";
    }

    @Override
    public OrderToDishListResponse readyOrder(Long id) {
        KitchenOrder kitchenOrder = findKitchenOrderById(id);

        if (!Status.READY.equals(kitchenOrder.getStatus())) {
            throw OrderNotReadyException.builder().message("Заказ еще не готов").httpStatus(HttpStatus.TOO_EARLY).build();
        }

        List<OrderToDish> orderToDishList = orderToDishCustomMapper.findById(id);
        List<OrderToDishResponse> orderToDishResponseList = orderToDishList.stream()
                .map(orderToDish -> {
                    Dish dish = findDishById(orderToDish.getDishId());
                    return orderToDishMapper.toOrderToDishResponse(kitchenOrderMapper.toKitchenOrderResponse(kitchenOrder), dishMapper.toDishResponse(dish));
                })
                .toList();

        return OrderToDishListResponse.builder().orderToDishResponseList(orderToDishResponseList).build();
    }

    @Override
    public OrderToDishListResponse getKitchenList() {
        List<OrderToDish> orderToDishCustomMapperAll = orderToDishCustomMapper.findAll();
        List<OrderToDishResponse> orderToDishResponseList = orderToDishCustomMapperAll.stream()
                .map(orderToDish -> {
                    KitchenOrder kitchenOrder = findKitchenOrderById(orderToDish.getKitchenOrderId());
                    Dish dish = findDishById(orderToDish.getDishId());
                    return orderToDishMapper.toOrderToDishResponse(kitchenOrderMapper.toKitchenOrderResponse(kitchenOrder), dishMapper.toDishResponse(dish));
                })
                .toList();

        return OrderToDishListResponse.builder().orderToDishResponseList(orderToDishResponseList).build();
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
