package project.modules.item.messaging;

import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import project.item.ItemStatus;
import project.item.messaging.ItemMessagingService;
import project.item.messaging.dto.ItemProcessingRequest;

@ExtendWith(MockitoExtension.class)
class ItemMessagingServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private Queue itemRequestQueue;
    private ItemMessagingService messagingService;

    @BeforeEach
    void setUp() {
        itemRequestQueue = new Queue("item.processing.request", true);
        messagingService = new ItemMessagingService(rabbitTemplate, itemRequestQueue);
    }

    @Test
    @DisplayName("sendProcessingRequest publishes payload to request queue")
    void sendProcessingRequestDelegatesToRabbitTemplate() {
        ItemProcessingRequest payload = new ItemProcessingRequest(UUID.randomUUID(), ItemStatus.PENDING);

        messagingService.sendProcessingRequest(payload);

        verify(rabbitTemplate).convertAndSend(itemRequestQueue.getName(), payload);
    }
}
