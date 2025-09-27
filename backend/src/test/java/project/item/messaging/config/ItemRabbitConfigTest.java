package project.item.messaging.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;

class ItemRabbitConfigTest {

    private ItemRabbitConfig config;

    @BeforeEach
    void setUp() {
        config = new ItemRabbitConfig();
    }

    @Test
    @DisplayName("itemRequestQueue is durable and uses configured name")
    void itemRequestQueueProperties() {
        Queue queue = config.itemRequestQueue();

        assertThat(queue.getName()).isEqualTo(config.requestQueueName);
        assertThat(queue.isDurable()).isTrue();
        assertThat(queue.isAutoDelete()).isFalse();
    }

    @Test
    @DisplayName("itemResponseQueue is durable and uses configured name")
    void itemResponseQueueProperties() {
        Queue queue = config.itemResponseQueue();

        assertThat(queue.getName()).isEqualTo(config.responseQueueName);
        assertThat(queue.isDurable()).isTrue();
        assertThat(queue.isAutoDelete()).isFalse();
    }
}
