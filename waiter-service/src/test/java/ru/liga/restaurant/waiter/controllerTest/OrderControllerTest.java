package ru.liga.restaurant.waiter.controllerTest;

import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
import ru.liga.restaurant.waiter.model.entity.WaiterOrder;
import ru.liga.restaurant.waiter.model.response.OrderResponse;
import ru.liga.restaurant.waiter.repository.MenuRepository;
import ru.liga.restaurant.waiter.repository.OrderPositionsRepository;
import ru.liga.restaurant.waiter.repository.PaymentRepository;
import ru.liga.restaurant.waiter.repository.WaiterAccountRepository;
import ru.liga.restaurant.waiter.repository.WaiterOrderRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final EasyRandom generator;
    private final WaiterOrderMapper waiterOrderMapper;
    @MockBean
    private KitchenFeignClient kitchenFeignClient;
    @MockBean
    private KafkaSender kafkaSender;
    @MockBean
    private WaiterOrderRepository waiterOrderRepository;
    @MockBean
    private WaiterAccountRepository waiterAccountRepository;
    @MockBean
    private OrderPositionsRepository orderPositionsRepository;
    @MockBean
    private MenuRepository menuRepository;
    @MockBean
    private PaymentRepository paymentRepository;

    OrderControllerTest() {
        this.generator = new EasyRandom();
        PaymentMapper paymentMapper = new PaymentMapperImpl();
        WaiterAccountMapper waiterAccountMapper = new WaiterAccountMapperImpl();
        OrderPositionsMapper orderPositionsMapper = new OrderPositionsMapperImpl();
        waiterOrderMapper = new WaiterOrderMapperImpl(paymentMapper, waiterAccountMapper, orderPositionsMapper);
    }

    @Test
    @SneakyThrows
    void shouldGetOrder() {
        WaiterOrder waiterOrder = generator.nextObject(WaiterOrder.class);
        OrderResponse orderResponse = waiterOrderMapper.toOrderResponse(waiterOrder);

        Mockito.when(waiterOrderRepository.findByOrderNo(waiterOrder.getOrderNo())).thenReturn(Optional.of(waiterOrder));

        mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}", waiterOrder.getOrderNo()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNo").value(waiterOrder.getOrderNo()))
                .andExpect(jsonPath("$.waiterStatus").value(waiterOrder.getStatus().toString()))
                .andExpect(jsonPath("$.waiterAccount").value(orderResponse.getWaiterAccount()))
                .andExpect(jsonPath("$.tableNo").value(waiterOrder.getTableNo()))
                .andExpect(jsonPath("$.payment").exists())
                .andExpect(jsonPath("$.positions").exists());
    }
}