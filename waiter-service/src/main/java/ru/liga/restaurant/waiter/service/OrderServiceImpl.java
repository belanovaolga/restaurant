package ru.liga.restaurant.waiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.restaurant.waiter.client.KitchenClient;
import ru.liga.restaurant.waiter.exception.NotFoundException;
import ru.liga.restaurant.waiter.mapper.OrderPositionsMapper;
import ru.liga.restaurant.waiter.mapper.WaiterOrderMapper;
import ru.liga.restaurant.waiter.model.*;
import ru.liga.restaurant.waiter.model.enums.WaiterStatus;
import ru.liga.restaurant.waiter.model.request.DishRequest;
import ru.liga.restaurant.waiter.model.request.OrderRequest;
import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;
import ru.liga.restaurant.waiter.model.response.OrderResponse;
import ru.liga.restaurant.waiter.model.response.OrderResponseList;
import ru.liga.restaurant.waiter.repository.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final WaiterOrderRepository waiterOrderRepository;
    private final WaiterAccountRepository waiterAccountRepository;
    private final OrderPositionsRepository orderPositionsRepository;
    private final MenuRepository menuRepository;
    private final PaymentRepository paymentRepository;
    private final WaiterOrderMapper waiterOrderMapper;
    private final OrderPositionsMapper orderPositionsMapper;
    private final KitchenClient kitchenClient;

    @Override
    public OrderResponseList getOrderList() {
        List<WaiterOrder> waiterOrders = waiterOrderRepository.findAll();
        List<OrderResponse> orderResponseList = waiterOrders.stream().map(waiterOrderMapper::toOrderResponse).toList();

        return OrderResponseList.builder().orderResponseList(orderResponseList).build();
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return waiterOrderMapper.toOrderResponse(findById(id));
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        WaiterAccount waiterAccount = waiterAccountRepository.findById(orderRequest.getWaiterId())
                .orElseThrow(() -> NotFoundException.builder()
                        .message("Официант не найден")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build());
        WaiterOrder waiterOrder = waiterOrderMapper.toWaiterOrder(orderRequest, waiterAccount);
        WaiterOrder savedOrder = waiterOrderRepository.save(waiterOrder);

        List<OrderPositions> positions = orderRequest.getPositions().stream()
                .map(orderPositionsRequest -> {
                    OrderPositions orderPositions = orderPositionsMapper.toOrderPositions(orderPositionsRequest, savedOrder);
                    orderPositions.setMenuPositionId(findMenuById(orderPositionsRequest.getMenuPositionId()));
                    return orderPositionsRepository.save(orderPositions);
                })
                .collect(Collectors.toList());
        savedOrder.setPositions(positions);

        Double sum = calculateTotalSum(positions);
        Payment payment = createPayment(savedOrder, orderRequest.getPaymentType(), sum);
        savedOrder.setPayment(payment);

        WaiterOrder finalOrder = waiterOrderRepository.save(savedOrder);

//        отдаем заказ кухне
        List<DishRequest> dishRequest = finalOrder.getPositions().stream()
                .map(orderPositions -> {
                    return DishRequest.builder()
                            .dishId(orderPositions.getMenuPositionId().getId())
                            .dishesNumber(orderPositions.getDishNum())
                            .build();
                })
                .toList();
        OrderToDishRequest orderToDishRequest = OrderToDishRequest.builder()
                .kitchenOrderId(finalOrder.getOrderNo())
                .waiterOrderNo(finalOrder.getWaiterId().getWaiterId())
                .dishRequest(dishRequest)
                .build();
        kitchenClient.acceptOrder(orderToDishRequest);

        return waiterOrderMapper.toOrderResponse(finalOrder);
    }

    @Override
    public String getStatus(Long id) {
        return findById(id).getWaiterStatus().toString();
    }

    @Override
    public void orderReady(Long id) {
        WaiterOrder waiterOrder = findById(id);
        waiterOrder.setWaiterStatus(WaiterStatus.READY);
        waiterOrderRepository.save(waiterOrder);
    }

    private WaiterOrder findById(Long id) {
        return waiterOrderRepository.findById(id).orElseThrow(() ->
                NotFoundException.builder().message("Заказ не найден").httpStatus(HttpStatus.NOT_FOUND).build());
    }

    private Menu findMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() ->
                NotFoundException.builder().message("Блюдо " + id + " не найдено").httpStatus(HttpStatus.NOT_FOUND).build());
    }

    private Double calculateTotalSum(List<OrderPositions> positions) {
        return positions.stream()
                .mapToDouble(orderPositions ->
                        orderPositions.getMenuPositionId().getDishCost() * orderPositions.getDishNum())
                .sum();
    }

    private Payment createPayment(WaiterOrder order, String paymentType, Double sum) {
        Payment payment = Payment.builder()
                .order(order)
                .paymentType(paymentType)
                .paymentDate(ZonedDateTime.now())
                .paymentSum(sum)
                .build();
        return paymentRepository.save(payment);
    }
}
