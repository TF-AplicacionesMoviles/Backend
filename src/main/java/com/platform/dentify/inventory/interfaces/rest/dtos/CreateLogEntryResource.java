package com.platform.dentify.inventory.interfaces.rest.dtos;

public record CreateLogEntryResource(Long itemId, int consumedQuantity, Long invoiceId) {
}
