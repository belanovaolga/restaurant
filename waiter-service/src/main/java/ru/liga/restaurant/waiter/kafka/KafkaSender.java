package ru.liga.restaurant.waiter.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.liga.restaurant.waiter.exception.OrderSerializationException;
import ru.liga.restaurant.waiter.model.request.OrderToDishRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {
    @Value("${spring.kafka.producer.number-topic}")
    private String numberTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrder(OrderToDishRequest order) {
        log.info("Начало обработки соообщения на отправку");
        String orderJson = convertToString(order);
        kafkaTemplate.send(numberTopic, orderJson);
        log.info("Сообщение о заказе отправлено");
    }

    private String convertToString(OrderToDishRequest order) {
        try {
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            log.error("Не удалось сериализовать заказ: {}", order, e);
            throw OrderSerializationException.builder()
                    .message("Ошибка при сериализации заказa " + order)
                    .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                    .build();
        }
    }
}