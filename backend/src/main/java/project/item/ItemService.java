package project.item;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item createItem() {
        Item item = new Item();
        return itemRepository.save(item);
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