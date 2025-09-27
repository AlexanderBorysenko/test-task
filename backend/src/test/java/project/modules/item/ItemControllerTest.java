package project.modules.item;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import project.item.Item;
import project.item.ItemController;
import project.item.ItemService;
import project.item.ItemStatus;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ItemController(itemService)).build();
    }

    @Test
    @DisplayName("GET /items/ returns all items")
    void getAllShouldReturnItems() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(id, ItemStatus.PENDING);
        given(itemService.getAllItems()).willReturn(List.of(item));

        mockMvc.perform(get("/items/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].status").value(ItemStatus.PENDING.name()))
                .andExpect(jsonPath("$[0].name").value("Test item"));

        verify(itemService).getAllItems();
    }

    @Test
    @DisplayName("POST /items/ delegates to service and returns created item")
    void createShouldReturnNewItem() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(id, ItemStatus.PENDING);
        given(itemService.createItem()).willReturn(item);

        mockMvc.perform(post("/items/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.status").value(ItemStatus.PENDING.name()))
                .andExpect(jsonPath("$.name").value("Created item"));

        verify(itemService).createItem();
    }
}
