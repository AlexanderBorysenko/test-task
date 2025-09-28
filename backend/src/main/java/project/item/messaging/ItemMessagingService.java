package project.item.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import project.item.messaging.dto.ItemProcessingRequest;

@Service
@RequiredArgsConstructor
public class ItemMessagingService {
    private final RabbitTemplate rabbitTemplate;
    private final Queue itemRequestQueue;

    public void sendProcessingRequest(ItemProcessingRequest payload) {
        rabbitTemplate.convertAndSend(itemRequestQueue.getName(), payload);
    }
}