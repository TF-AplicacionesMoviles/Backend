package com.platform.dentify.inventory.interfaces.rest.dtos;

public record LogEntryResource(Long id, Long itemId, int consumedQuantity, Long invoiceId) {

}
