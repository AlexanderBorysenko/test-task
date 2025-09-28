package project.modules.item.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.item.Item;
import project.item.ItemRepository;
import project.item.ItemService;
import project.item.ItemStatus;
import project.item.messaging.ItemMessagingService;
import project.item.messaging.dto.ItemProcessingRequest;

@ExtendWith(MockitoExtension.class)
class ItemServiceMessagingTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMessagingService itemMessagingService;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository, itemMessagingService);
    }

    @Test
    @DisplayName("createItem persists entity and dispatches processing request to RabbitMQ")
    void createItemDispatchesProcessingRequest() {
        UUID id = UUID.randomUUID();
        Item persisted = new Item(id, ItemStatus.PENDING);

        when(itemRepository.save(any(Item.class))).thenReturn(persisted);

        Item result = itemService.createItem();

        ArgumentCaptor<ItemProcessingRequest> requestCaptor = ArgumentCaptor.forClass(ItemProcessingRequest.class);

        verify(itemRepository).save(any(Item.class));
        verify(itemMessagingService).sendProcessingRequest(requestCaptor.capture());

        ItemProcessingRequest request = requestCaptor.getValue();
        assertThat(request.id()).isEqualTo(id);
        assertThat(request.status()).isEqualTo(ItemStatus.PENDING);
        assertThat(result).isEqualTo(persisted);
    }
}
