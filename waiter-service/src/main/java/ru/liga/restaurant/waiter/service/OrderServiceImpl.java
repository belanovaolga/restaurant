package ru.liga.restaurant.waiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.liga.restaurant.waiter.repository.OrderRepository;
import ru.liga.restaurant.waiter.request.OrderRequest;
import ru.liga.restaurant.waiter.dto.OrderDto;
import ru.liga.restaurant.waiter.dto.Status;
import ru.liga.restaurant.waiter.exception.OrderAlreadyExistException;
import ru.liga.restaurant.waiter.exception.OrderNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<OrderDto> getOrderList() {
        return orderRepository.findAll();
    }

    @Override
    public OrderDto getOrder(Long id) {
        return findById(id);
    }

    @Override
    public OrderDto createOrder(OrderRequest orderRequest) {
        if (orderRepository.findById(orderRequest.getId()).isPresent()) {
            throw OrderAlreadyExistException.builder().message("Заказ с таким идентификатором уже существует").httpStatus(HttpStatus.CONFLICT).build();
        }

        OrderDto orderDto = OrderDto.builder()
                .id(orderRequest.getId())
                .dishes(orderRequest.getDishes())
                .sum(orderRequest.getSum())
                .waiterId(orderRequest.getWaiterId())
                .status(Status.ACCEPT)
                .build();

        return orderRepository.save(orderDto);
    }

    @Override
    public String getStatus(Long id) {
        return findById(id).getStatus().toString();
    }

    private OrderDto findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> OrderNotFoundException.builder().message("Заказ не найден").httpStatus(HttpStatus.NOT_FOUND).build());
    }
}
