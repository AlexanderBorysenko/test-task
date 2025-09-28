package project.item.messaging.dto;

import java.util.UUID;

import project.item.ItemStatus;

public record ItemProcessingRequest(
        UUID id,
        ItemStatus status) {
}
