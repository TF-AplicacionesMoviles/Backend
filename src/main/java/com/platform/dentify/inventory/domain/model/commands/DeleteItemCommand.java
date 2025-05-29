package com.platform.dentify.inventory.domain.model.commands;

public record DeleteItemCommand(Long id) {

    public DeleteItemCommand {
        if(id == null  || id <= 0) {
            throw new IllegalArgumentException("Id can not be null");
        }
    }
}
