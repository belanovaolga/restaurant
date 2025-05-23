package ru.liga.restaurant.kitchen.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.restaurant.kitchen.model.request.OrderToDishRequest;
import ru.liga.restaurant.kitchen.service.KitchenService;

import java.io.IOException;

/**
 * Сервис для обработки сообщений о заказах, поступающих из Kafka
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final KitchenService kitchenService;
    private final ObjectMapper objectMapper;

    /**
     * Обрабатывает JSON-сообщение о заказе
     *
     * @param orderJson JSON-строка с данными заказа
     * @throws IOException      если не удалось десериализовать JSON
     * @throws RuntimeException если произошла ошибка при обработке заказа
     */
    public void processOrder(String orderJson) {
        try {
            log.info("Начата обработка сообщения о заказе: {}", orderJson);
            OrderToDishRequest order = objectMapper.readValue(orderJson, OrderToDishRequest.class);
            kitchenService.acceptOrder(order);
        } catch (IOException e) {
            log.error("Не удалось преобразовать заказ из JSON: {}", orderJson, e);
        } catch (Exception e) {
            log.error("Не удалось обработать заказ: {}", orderJson, e);
        }
    }
}