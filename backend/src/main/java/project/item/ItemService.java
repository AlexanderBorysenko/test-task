package project.item;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.item.messaging.ItemMessagingService;
import project.item.messaging.dto.ItemProcessingRequest;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMessagingService itemMessagingService;

    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item createItem() {
        Item item = new Item();

        Item itemFromRepository = itemRepository.save(item);

        itemMessagingService.sendProcessingRequest(
                new ItemProcessingRequest(itemFromRepository.getId(), itemFromRepository.getStatus()));

        return itemFromRepository;
    }

    @Transactional
    public Item updateItemStatus(UUID id, ItemStatus status) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        item.setStatus(status);
        return item;
    }

    @Transactional
    public Item markDone(UUID id) {
        return updateItemStatus(id, ItemStatus.DONE);
    }
}