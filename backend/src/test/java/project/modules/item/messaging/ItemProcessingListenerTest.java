package project.modules.item.messaging;

import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.item.ItemService;
import project.item.messaging.ItemProcessingListener;
import project.item.messaging.dto.ItemProcessingResponse;

@ExtendWith(MockitoExtension.class)
class ItemProcessingListenerTest {

    @Mock
    private ItemService itemService;

    private ItemProcessingListener listener;

    @BeforeEach
    void setUp() {
        listener = new ItemProcessingListener(itemService);
    }

    @Test
    @DisplayName("handle marks item as done")
    void handleMarksItemAsDone() {
        UUID id = UUID.randomUUID();
        ItemProcessingResponse payload = new ItemProcessingResponse(id);

        listener.handle(payload);

        verify(itemService).markDone(id);
    }
}
