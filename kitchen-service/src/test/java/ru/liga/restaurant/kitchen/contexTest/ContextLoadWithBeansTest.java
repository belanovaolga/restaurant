package ru.liga.restaurant.kitchen.contexTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ru.liga.restaurant.kitchen.controller.KitchenController;
import ru.liga.restaurant.kitchen.custom.repository.DishCustomRepository;
import ru.liga.restaurant.kitchen.custom.repository.KitchenOrderCustomRepository;
import ru.liga.restaurant.kitchen.custom.repository.OrderToDishCustomRepository;
import ru.liga.restaurant.kitchen.service.KitchenService;

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
        assertNotNull(applicationContext.getBean(KitchenController.class), "KitchenController не найден");
        assertNotNull(applicationContext.getBean(KitchenService.class), "KitchenService не найден");
        assertNotNull(applicationContext.getBean(DishCustomRepository.class), "DishCustomRepository не найден");
        assertNotNull(applicationContext.getBean(KitchenOrderCustomRepository.class), "KitchenOrderCustomRepository не найден");
        assertNotNull(applicationContext.getBean(OrderToDishCustomRepository.class), "OrderToDishCustomRepository не найден");
    }
}