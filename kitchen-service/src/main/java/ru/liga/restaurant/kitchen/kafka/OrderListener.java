package ru.liga.restaurant.kitchen.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Класс для прослушивания сообщений из топика Kafka
 */
@Component
@RequiredArgsConstructor
public class OrderListener {
    private final MessageService messageService;

    /**
     * Обрабатывает входящее сообщение из топика Kafka
     *
     * @param orderJson JSON-строка с данными заказа
     */
    @KafkaListener(topics = "orderToKitchenTopic", groupId = "group1")
    public void receiveOrder(String orderJson) {
        messageService.processOrder(orderJson);
    }
}