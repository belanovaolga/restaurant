package ru.liga.restaurant.waiter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic orderToKitchenTopic() {
        return TopicBuilder.name("orderToKitchenTopic").partitions(2).build();
    }
}