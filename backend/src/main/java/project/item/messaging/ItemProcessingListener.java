package project.item.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import project.item.ItemService;
import project.item.messaging.dto.ItemProcessingResponse;

@Component
@RequiredArgsConstructor
public class ItemProcessingListener {
    private final ItemService itemService;

    @RabbitListener(queues = "#{itemResponseQueue.name}")
    public void handle(ItemProcessingResponse payload) {
        itemService.markDone(payload.id());
    }
}