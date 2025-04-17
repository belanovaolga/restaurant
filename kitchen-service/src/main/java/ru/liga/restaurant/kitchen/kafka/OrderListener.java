package ru.liga.restaurant.kitchen.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {
    private final MessageService messageService;

    @KafkaListener(topics = "orderToKitchenTopic", groupId = "group1")
    public void receiveOrder(String orderJson) {
        messageService.processOrder(orderJson);
    }
}