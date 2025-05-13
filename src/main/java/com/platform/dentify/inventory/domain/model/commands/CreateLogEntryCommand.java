package com.platform.dentify.inventory.domain.model.commands;

public record CreateLogEntryCommand(Long itemId, int consumedQuantity, Long invoiceId) {

    public CreateLogEntryCommand {
        if(itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("Invalid item id");
        }

        if(consumedQuantity <= 0) {
            throw new IllegalArgumentException("Consumed quantity must be greater than zero");
        }

        if(invoiceId == null || invoiceId <= 0) {
            throw new IllegalArgumentException("Invalid invoice id");
        }
    }
}
