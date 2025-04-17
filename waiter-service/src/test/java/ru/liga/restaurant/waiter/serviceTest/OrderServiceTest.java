package ru.liga.restaurant.waiter.serviceTest;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.feign.KitchenFeignClient;
import ru.liga.restaurant.waiter.kafka.KafkaSender;
import ru.liga.restaurant.waiter.mapper.OrderPositionsMapper;
import ru.liga.restaurant.waiter.mapper.OrderPositionsMapperImpl;
import ru.liga.restaurant.waiter.mapper.PaymentMapper;
import ru.liga.restaurant.waiter.mapper.PaymentMapperImpl;
import ru.liga.restaurant.waiter.mapper.WaiterAccountMapper;
import ru.liga.restaurant.waiter.mapper.WaiterAccountMapperImpl;
import ru.liga.restaurant.waiter.mapper.WaiterOrderMapper;
import ru.liga.restaurant.waiter.mapper.WaiterOrderMapperImpl;
import ru.liga.restaurant.waiter.model.Menu;
import ru.liga.restaurant.waiter.model.OrderPositions;
import ru.liga.restaurant.waiter.model.Payment;
import ru.liga.restaurant.waiter.model.WaiterAccount;
import ru.liga.restaurant.waiter.model.WaiterOrder;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;
import ru.liga.restaurant.waiter.model.request.DishRequest;
import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;
import ru.liga.restaurant.waiter.model.response.OrderResponseList;
import ru.liga.restaurant.waiter.model.response.StatusResponse;
import ru.liga.restaurant.waiter.repository.MenuRepository;
import ru.liga.restaurant.waiter.repository.OrderPositionsRepository;
import ru.liga.restaurant.waiter.repository.PaymentRepository;
import ru.liga.restaurant.waiter.repository.WaiterAccountRepository;
import ru.liga.restaurant.waiter.repository.WaiterOrderRepository;
import ru.liga.restaurant.waiter.service.OrderService;
import ru.liga.restaurant.waiter.service.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class OrderServiceTest {
    private final WaiterOrderRepository waiterOrderRepository;
    private final WaiterAccountRepository waiterAccountRepository;
    private final OrderPositionsRepository orderPositionsRepository;
    private final MenuRepository menuRepository;
    private final PaymentRepository paymentRepository;
    private final WaiterOrderMapper waiterOrderMapper;
    private final OrderPositionsMapper orderPositionsMapper;
    private final KitchenFeignClient kitchenFeignClient;
    private final KafkaSender kafkaSender;
    private final OrderService orderService;
    private final EasyRandom generator;

    public OrderServiceTest() {
        waiterOrderRepository = Mockito.mock(WaiterOrderRepository.class);
        waiterAccountRepository = Mockito.mock(WaiterAccountRepository.class);
        orderPositionsRepository = Mockito.mock(OrderPositionsRepository.class);
        menuRepository = Mockito.mock(MenuRepository.class);
        paymentRepository = Mockito.mock(PaymentRepository.class);
        WaiterAccountMapper waiterAccountMapper = new WaiterAccountMapperImpl();
        PaymentMapper paymentMapper = new PaymentMapperImpl();
        orderPositionsMapper = new OrderPositionsMapperImpl();
        waiterOrderMapper = new WaiterOrderMapperImpl(paymentMapper, waiterAccountMapper, orderPositionsMapper);
        kitchenFeignClient = Mockito.mock(KitchenFeignClient.class);
        kafkaSender = Mockito.mock(KafkaSender.class);
        orderService = new OrderServiceImpl(waiterOrderRepository, waiterAccountRepository, orderPositionsRepository,
                menuRepository, paymentRepository, waiterOrderMapper, orderPositionsMapper, kitchenFeignClient, kafkaSender);

        generator = new EasyRandom();
    }

    @Test
    void shouldGetOrderList() {
        int pageNumber = 2;
        int size = 3;
        List<WaiterOrder> waiterOrders = new ArrayList<>();
        waiterOrders.add(generator.nextObject(WaiterOrder.class));
        waiterOrders.add(generator.nextObject(WaiterOrder.class));
        waiterOrders.add(generator.nextObject(WaiterOrder.class));
        waiterOrders.add(generator.nextObject(WaiterOrder.class));
        Page<WaiterOrder> waiterOrderPage = new PageImpl<>(waiterOrders);
        Mockito.when(waiterOrderRepository.findAll(PageRequest.of(pageNumber - 1, size))).thenReturn(waiterOrderPage);

        List<OrderResponse> orderResponseList = waiterOrders.stream().map(waiterOrderMapper::toOrderResponse).toList();
        OrderResponseList expectedOrderResponseList = OrderResponseList.builder()
                .orderResponsesList(orderResponseList)
                .build();

        OrderResponseList actualOrderresponseList = orderService.getOrderList(pageNumber, size);

        assertEquals(expectedOrderResponseList, actualOrderresponseList);
    }

    @Test
    void shouldGetOrder() {
        WaiterOrder waiterOrder = generator.nextObject(WaiterOrder.class);
        Optional<WaiterOrder> optionalWaiterOrder = Optional.of(waiterOrder);
        Mockito.when(waiterOrderRepository.findById(waiterOrder.getOrderNo())).thenReturn(optionalWaiterOrder);
        OrderResponse expectedOrderResponse = waiterOrderMapper.toOrderResponse(waiterOrder);

        OrderResponse actualOrderResponse = orderService.getOrder(waiterOrder.getOrderNo());

        assertEquals(expectedOrderResponse, actualOrderResponse);
    }

    @Test
    void shouldGetOrder_whenWaiterOrderNotFound() {
        Long id = RandomGenerator.getDefault().nextLong();
        Mockito.when(waiterOrderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderService.getOrder(id);
        });
    }

    @Test
    void shouldCreateOrder() {
        OrderRequest orderRequest = generator.nextObject(OrderRequest.class);
        WaiterAccount waiterAccount = generator.nextObject(WaiterAccount.class);
        waiterAccount.setWaiterId(orderRequest.getWaiterId());
        WaiterOrder waiterOrder = waiterOrderMapper.toWaiterOrder(orderRequest, waiterAccount);
        Payment payment = generator.nextObject(Payment.class);
        waiterOrder.setPayment(payment);

        Mockito.doNothing().when(kitchenFeignClient).rejectOrder(orderRequest);
        Mockito.when(waiterAccountRepository.findById(orderRequest.getWaiterId())).thenReturn(Optional.of(waiterAccount));
        Mockito.when(waiterOrderRepository.save(any(WaiterOrder.class))).thenReturn(waiterOrder);
        List<OrderPositions> positions = new ArrayList<>();
        List<DishRequest> dishRequest = orderRequest.getPositions().stream()
                .map(orderPositionsRequest -> {
                    Menu menu = generator.nextObject(Menu.class);
                    menu.setId(orderPositionsRequest.getMenuPositionId());

                    OrderPositions orderPositions = orderPositionsMapper.toOrderPositions(orderPositionsRequest, waiterOrder);
                    orderPositions.setMenu(menu);
                    positions.add(orderPositions);

                    Mockito.when(menuRepository.findById(orderPositionsRequest.getMenuPositionId()))
                            .thenReturn(Optional.of(menu));
                    Mockito.when(orderPositionsRepository.save(any(OrderPositions.class)))
                            .thenAnswer(invocation -> invocation.getArgument(0));

                    return DishRequest.builder()
                            .dishId(orderPositions.getMenu().getId())
                            .dishesNumber(orderPositions.getDishNum())
                            .build();
                })
                .toList();
        waiterOrder.setPositions(positions);
        Mockito.when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        OrderToDishRequest orderToDishRequest = OrderToDishRequest.builder()
                .kitchenOrderId(waiterOrder.getOrderNo())
                .waiterOrderNo(waiterOrder.getWaiterAccount().getWaiterId())
                .dishRequest(dishRequest)
                .build();
        Mockito.doNothing().when(kafkaSender).sendOrder(orderToDishRequest);

        OrderResponse expectedOrderResponse = waiterOrderMapper.toOrderResponse(waiterOrder);

        OrderResponse actualOrderResponse = orderService.createOrder(orderRequest);

        assertEquals(expectedOrderResponse, actualOrderResponse);
    }

    @Test
    void shouldCreateOrder_whenWaiterAccountNotFound() {
        OrderRequest orderRequest = generator.nextObject(OrderRequest.class);

        Mockito.doNothing().when(kitchenFeignClient).rejectOrder(orderRequest);
        Mockito.when(waiterOrderRepository.findById(orderRequest.getWaiterId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderService.createOrder(orderRequest);
        });
    }

    @Test
    void shouldCreateOrder_whenPositionNotFound() {
        OrderRequest orderRequest = generator.nextObject(OrderRequest.class);
        WaiterAccount waiterAccount = generator.nextObject(WaiterAccount.class);
        waiterAccount.setWaiterId(orderRequest.getWaiterId());
        WaiterOrder waiterOrder = waiterOrderMapper.toWaiterOrder(orderRequest, waiterAccount);

        Mockito.doNothing().when(kitchenFeignClient).rejectOrder(orderRequest);
        Mockito.when(waiterAccountRepository.findById(orderRequest.getWaiterId())).thenReturn(Optional.of(waiterAccount));
        Mockito.when(waiterOrderRepository.save(any(WaiterOrder.class)))
                .thenReturn(waiterOrder);
        orderRequest.getPositions()
                .forEach(orderPositionsRequest -> {
                    OrderPositions orderPositions = orderPositionsMapper.toOrderPositions(orderPositionsRequest, waiterOrder);
                    Menu menu = generator.nextObject(Menu.class);
                    menu.setId(orderPositionsRequest.getMenuPositionId());
                    orderPositions.setMenu(menu);

                    Mockito.when(menuRepository.findById(orderPositionsRequest.getMenuPositionId()))
                            .thenReturn(Optional.empty());
                });

        assertThrows(NotFoundException.class, () -> {
            orderService.createOrder(orderRequest);
        });
    }

    @Test
    void shouldGetStatus() {
        WaiterOrder waiterOrder = generator.nextObject(WaiterOrder.class);
        StatusResponse expectedStatusResponse = StatusResponse.builder().waiterStatus(waiterOrder.getStatus()).build();

        Mockito.when(waiterOrderRepository.findById(waiterOrder.getOrderNo())).thenReturn(Optional.of(waiterOrder));

        StatusResponse actualStatus = orderService.getStatus(waiterOrder.getOrderNo());

        assertEquals(expectedStatusResponse, actualStatus);
    }

    @Test
    void shouldGetStatus_whenWaiterOrderNotFound() {
        WaiterOrder waiterOrder = generator.nextObject(WaiterOrder.class);
        Mockito.when(waiterOrderRepository.findById(waiterOrder.getOrderNo())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderService.getStatus(waiterOrder.getOrderNo());
        });
    }

    @Test
    void shouldOrderReady() {
        WaiterOrder waiterOrder = generator.nextObject(WaiterOrder.class);
        Mockito.doNothing().when(waiterOrderRepository).updateStatus(waiterOrder.getOrderNo(), WaiterStatus.READY);

        assertDoesNotThrow(() -> orderService.orderReady(waiterOrder.getOrderNo()));
    }

}