package ru.liga.restaurant.kitchen.serviceTest;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.liga.restaurant.kitchen.custom.repository.DishCustomRepository;
import ru.liga.restaurant.kitchen.custom.repository.KitchenOrderCustomRepository;
import ru.liga.restaurant.kitchen.custom.repository.OrderToDishCustomRepository;
import ru.liga.restaurant.kitchen.exception.InsufficientStockException;
import ru.liga.restaurant.kitchen.exception.NotFoundException;
import ru.liga.restaurant.kitchen.feign.WaiterFeignClient;
import ru.liga.restaurant.kitchen.mapper.DishMapper;
import ru.liga.restaurant.kitchen.mapper.DishMapperImpl;
import ru.liga.restaurant.kitchen.mapper.KitchenOrderMapper;
import ru.liga.restaurant.kitchen.mapper.KitchenOrderMapperImpl;
import ru.liga.restaurant.kitchen.mapper.OrderToDishMapper;
import ru.liga.restaurant.kitchen.mapper.OrderToDishMapperImpl;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.entity.KitchenOrder;
import ru.liga.restaurant.kitchen.model.entity.OrderToDish;
import ru.liga.restaurant.kitchen.model.enums.KitchenStatus;
import ru.liga.restaurant.kitchen.model.request.OrderRequest;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.model.response.DishResponse;
import ru.liga.restaurant.kitchen.model.response.KitchenOrderResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishListResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponse;
import ru.liga.restaurant.kitchen.model.response.OrderToDishResponseForKitchen;
import ru.liga.restaurant.kitchen.service.KitchenService;
import ru.liga.restaurant.kitchen.service.KitchenServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KitchenServiceTest {
    private final DishCustomRepository dishCustomRepository;
    private final KitchenOrderCustomRepository kitchenOrderCustomRepository;
    private final OrderToDishCustomRepository orderToDishCustomRepository;
    private final DishMapper dishMapper;
    private final KitchenOrderMapper kitchenOrderMapper;
    private final OrderToDishMapper orderToDishMapper;
    private final WaiterFeignClient waiterFeignClient;
    private final KitchenService kitchenService;
    private final EasyRandom generator;

    public KitchenServiceTest() {
        dishCustomRepository = Mockito.mock(DishCustomRepository.class);
        kitchenOrderCustomRepository = Mockito.mock(KitchenOrderCustomRepository.class);
        orderToDishCustomRepository = Mockito.mock(OrderToDishCustomRepository.class);
        dishMapper = new DishMapperImpl();
        kitchenOrderMapper = new KitchenOrderMapperImpl();
        orderToDishMapper = new OrderToDishMapperImpl();
        waiterFeignClient = Mockito.mock(WaiterFeignClient.class);
        kitchenService = new KitchenServiceImpl(dishCustomRepository, kitchenOrderCustomRepository, orderToDishCustomRepository,
                dishMapper, kitchenOrderMapper, orderToDishMapper, waiterFeignClient);
        generator = new EasyRandom();
    }

    @Test
    void shouldAcceptOrder() {
        OrderToDishRequest orderToDishRequest = generator.nextObject(OrderToDishRequest.class);

        KitchenOrder kitchenOrder = kitchenOrderMapper.toKitchenOrder(orderToDishRequest);
        Mockito.doNothing().when(kitchenOrderCustomRepository).insert(kitchenOrder);
        List<DishResponse> dishResponseList = orderToDishRequest.getDishRequest().stream()
                .map(dishRequest -> {
                    Dish dish = Dish.builder()
                            .dishId(dishRequest.getDishId())
                            .balance(dishRequest.getDishesNumber() + 1)
                            .shortName("dishName")
                            .dishComposition("composition")
                            .build();
                    Mockito.when(dishCustomRepository.findById(dishRequest.getDishId()))
                            .thenReturn(Optional.of(dish));

                    Mockito.doNothing().when(dishCustomRepository).update(dish);

                    OrderToDish orderToDish = orderToDishMapper.toOrderToDish(kitchenOrder, dish, dishRequest);
                    Mockito.doNothing().when(orderToDishCustomRepository).insert(orderToDish);

                    return dishMapper.toDishResponse(dish);
                })
                .toList();

        OrderToDishResponse expectedOrderToDishResponse = orderToDishMapper.toOrderToDishResponse(
                kitchenOrderMapper.toKitchenOrderResponse(kitchenOrder), dishResponseList);

        OrderToDishResponse actualOrderToDishResponse = kitchenService.acceptOrder(orderToDishRequest);
        KitchenOrderResponse kitchenOrderResponse = expectedOrderToDishResponse.getKitchenOrderResponse();
        kitchenOrderResponse.setCreateDate(actualOrderToDishResponse.getKitchenOrderResponse().getCreateDate());
        expectedOrderToDishResponse.setKitchenOrderResponse(kitchenOrderResponse);

        assertEquals(expectedOrderToDishResponse, actualOrderToDishResponse);
    }

    @Test
    void shouldAcceptOrder_whenInsufficientStock() {
        OrderToDishRequest orderToDishRequest = generator.nextObject(OrderToDishRequest.class);

        KitchenOrder kitchenOrder = kitchenOrderMapper.toKitchenOrder(orderToDishRequest);
        Mockito.doNothing().when(kitchenOrderCustomRepository).insert(kitchenOrder);
        orderToDishRequest.getDishRequest()
                .forEach(dishRequest -> {
                    Dish dish = Dish.builder()
                            .dishId(dishRequest.getDishId())
                            .balance(dishRequest.getDishesNumber() - 1)
                            .shortName("dishName")
                            .dishComposition("composition")
                            .build();
                    Mockito.when(dishCustomRepository.findById(dishRequest.getDishId()))
                            .thenReturn(Optional.of(dish));
                });

        assertThrows(InsufficientStockException.class, () -> {
            kitchenService.acceptOrder(orderToDishRequest);
        });
    }

    @Test
    void shouldAcceptOrder_whenDishNotFound() {
        OrderToDishRequest orderToDishRequest = generator.nextObject(OrderToDishRequest.class);

        KitchenOrder kitchenOrder = kitchenOrderMapper.toKitchenOrder(orderToDishRequest);
        Mockito.doNothing().when(kitchenOrderCustomRepository).insert(kitchenOrder);
        orderToDishRequest.getDishRequest()
                .forEach(dishRequest -> {
                    Mockito.when(dishCustomRepository.findById(dishRequest.getDishId()))
                            .thenReturn(Optional.empty());
                });

        assertThrows(NotFoundException.class, () -> {
            kitchenService.acceptOrder(orderToDishRequest);
        });
    }

    @Test
    void shouldRejectOrder() {
        OrderRequest orderRequest = generator.nextObject(OrderRequest.class);
        orderRequest.getPositions().forEach(orderPositionsRequest -> {
            Dish dish = Dish.builder()
                    .dishId(orderPositionsRequest.getMenuPositionId())
                    .balance(orderPositionsRequest.getDishNum() + 1)
                    .shortName("dishName")
                    .dishComposition("composition")
                    .build();


            Mockito.when(dishCustomRepository.findById(orderPositionsRequest.getMenuPositionId()))
                    .thenReturn(Optional.of(dish));
        });

        assertDoesNotThrow(() -> kitchenService.rejectOrder(orderRequest));
    }

    @Test
    void shouldRejectOrder_whenInsufficientStock() {
        OrderRequest orderRequest = generator.nextObject(OrderRequest.class);
        orderRequest.getPositions().forEach(orderPositionsRequest -> {
            Dish dish = Dish.builder()
                    .dishId(orderPositionsRequest.getMenuPositionId())
                    .balance(orderPositionsRequest.getDishNum() - 1)
                    .shortName("dishName")
                    .dishComposition("composition")
                    .build();


            Mockito.when(dishCustomRepository.findById(orderPositionsRequest.getMenuPositionId()))
                    .thenReturn(Optional.of(dish));
        });

        assertThrows(InsufficientStockException.class, () -> kitchenService.rejectOrder(orderRequest));
    }

    @Test
    void shouldRejectOrder_whenDishNotFound() {
        OrderRequest orderRequest = generator.nextObject(OrderRequest.class);
        orderRequest.getPositions().forEach(orderPositionsRequest -> {
            Mockito.when(dishCustomRepository.findById(orderPositionsRequest.getMenuPositionId())).thenReturn(Optional.empty());
        });

        assertThrows(NotFoundException.class, () -> {
            kitchenService.rejectOrder(orderRequest);
        });
    }

    @Test
    void shouldReadyOrder() {
        KitchenOrder kitchenOrder = generator.nextObject(KitchenOrder.class);
        Mockito.when(kitchenOrderCustomRepository.findById(kitchenOrder.getKitchenOrderId())).thenReturn(Optional.of(kitchenOrder));
        Mockito.doNothing().when(kitchenOrderCustomRepository).updateStatus(kitchenOrder);
        Mockito.doNothing().when(waiterFeignClient).orderReady(kitchenOrder.getWaiterOrderNo());

        kitchenService.readyOrder(kitchenOrder.getKitchenOrderId());

        assertEquals(KitchenStatus.READY, kitchenOrder.getStatus());
    }

    @Test
    void shouldReadyOrder_whenKitchenOrderNotFound() {
        KitchenOrder kitchenOrder = generator.nextObject(KitchenOrder.class);
        Mockito.when(kitchenOrderCustomRepository.findById(kitchenOrder.getKitchenOrderId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            kitchenService.readyOrder(kitchenOrder.getKitchenOrderId());
        });
    }

    @Test
    void shouldGetKitchenList() {
        OrderToDish orderToDish1 = generator.nextObject(OrderToDish.class);
        OrderToDish orderToDish2 = generator.nextObject(OrderToDish.class);
        OrderToDish orderToDish3 = generator.nextObject(OrderToDish.class);
        OrderToDish orderToDish4 = generator.nextObject(OrderToDish.class);
        List<OrderToDish> orderToDishList = List.of(orderToDish1, orderToDish2, orderToDish3, orderToDish4);
        List<OrderToDishResponseForKitchen> orderToDishResponseList = orderToDishList.stream()
                .map(orderToDish -> orderToDishMapper.toOrderToDishResponseForKitchen(
                        kitchenOrderMapper.toKitchenOrderResponse(orderToDish.getKitchenOrder()),
                        dishMapper.toDishResponse(orderToDish.getDish())
                ))
                .toList();
        OrderToDishListResponse expectedOrderToDishListResponse = OrderToDishListResponse.builder()
                .orderToDishResponseForKitchenList(orderToDishResponseList)
                .build();
        int pageNumber = 2;
        int size = 4;

        Mockito.when(orderToDishCustomRepository.findAll((pageNumber - 1) * size, size)).thenReturn(orderToDishList);

        OrderToDishListResponse actualOrderToDishListResponse = kitchenService.getKitchenList(pageNumber, size);

        assertEquals(expectedOrderToDishListResponse, actualOrderToDishListResponse);
    }
}