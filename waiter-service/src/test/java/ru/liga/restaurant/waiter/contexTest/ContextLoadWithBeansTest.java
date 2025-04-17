package ru.liga.restaurant.waiter.contexTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ru.liga.restaurant.waiter.controller.OrderController;
import ru.liga.restaurant.waiter.repository.MenuRepository;
import ru.liga.restaurant.waiter.repository.OrderPositionsRepository;
import ru.liga.restaurant.waiter.repository.PaymentRepository;
import ru.liga.restaurant.waiter.repository.WaiterAccountRepository;
import ru.liga.restaurant.waiter.repository.WaiterOrderRepository;
import ru.liga.restaurant.waiter.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ContextLoadWithBeansTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Spring контекст не поднялся");
    }

    @Test
    void verifyKeyBeans() {
        assertNotNull(applicationContext.getBean(OrderController.class), "OrderController не найден");
        assertNotNull(applicationContext.getBean(OrderService.class), "OrderService не найден");
        assertNotNull(applicationContext.getBean(MenuRepository.class), "MenuRepository не найден");
        assertNotNull(applicationContext.getBean(OrderPositionsRepository.class), "OrderPositionsRepository не найден");
        assertNotNull(applicationContext.getBean(PaymentRepository.class), "PaymentRepository не найден");
        assertNotNull(applicationContext.getBean(WaiterAccountRepository.class), "WaiterAccountRepository не найден");
        assertNotNull(applicationContext.getBean(WaiterOrderRepository.class), "WaiterOrderRepository не найден");
    }
}