package project.item.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ItemRabbitConfig {
    public final String requestQueueName = "item.processing.request";
    public final String responseQueueName = "item.processing.response";

    @Bean
    Queue itemRequestQueue() {
        return QueueBuilder.durable(requestQueueName).build();
    }

    @Bean
    Queue itemResponseQueue() {
        return QueueBuilder.durable(responseQueueName).build();
    }
}
