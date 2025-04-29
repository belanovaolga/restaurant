package ru.liga.restaurant.kitchen.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.liga.restaurant.kitchen.custom.repository.DishCustomRepository;
import ru.liga.restaurant.kitchen.model.entity.Dish;
import ru.liga.restaurant.kitchen.model.request.OrderRequest;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KitchenControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final EasyRandom generator;
    @MockBean
    private DishCustomRepository dishCustomRepository;

    KitchenControllerTest() {
        this.generator = new EasyRandom();
    }

    @Test
    @SneakyThrows
    void shouldCheckOrder() {
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

        mockMvc.perform(MockMvcRequestBuilders.post("/kitchen/check")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void shouldCheckOrder_whenInsufficientStock() {
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

        mockMvc.perform(MockMvcRequestBuilders.post("/kitchen/check")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isConflict());
    }
}