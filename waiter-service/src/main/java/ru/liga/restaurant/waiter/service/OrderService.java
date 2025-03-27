package ru.liga.restaurant.waiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.restaurant.waiter.dao.OrderDao;
import ru.liga.restaurant.waiter.dto.OrderDto;
import ru.liga.restaurant.waiter.dto.Status;
import ru.liga.restaurant.waiter.exception.OrderAlreadyExist;
import ru.liga.restaurant.waiter.exception.OrderNotFound;
import ru.liga.restaurant.waiter.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderDto> getOrderList() {
        return orderRepository.findAll();
    }

    public OrderDto getOrder(Long id) {
        return findById(id);
    }

    public OrderDto createOrder(OrderDao orderDao) {
        if(orderRepository.findById(orderDao.getId()).isPresent()) {
            throw OrderAlreadyExist.builder().message("An order with this id already exists").code(409).build();
        }

        OrderDto orderDto = OrderDto.builder()
                .id(orderDao.getId())
                .dishes(orderDao.getDishes())
                .sum(orderDao.getSum())
                .waiterId(orderDao.getWaiterId())
                .status(Status.ACCEPT)
                .build();

        return orderRepository.save(orderDto);
    }

    public String getStatus(Long id) {
        return findById(id).getStatus().toString();
    }

    private OrderDto findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> OrderNotFound.builder().message("Order not found").code(404).build());
    }
}
