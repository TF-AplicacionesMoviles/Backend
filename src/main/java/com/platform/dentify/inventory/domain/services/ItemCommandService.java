package com.platform.dentify.inventory.domain.services;

import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.inventory.domain.model.commands.CreateItemCommand;
import com.platform.dentify.inventory.domain.model.commands.DeleteItemCommand;
import com.platform.dentify.inventory.domain.model.commands.UpdateItemCommand;

import java.util.Optional;

public interface ItemCommandService {

    Long handle(CreateItemCommand command);

    Optional<Item> handle(UpdateItemCommand command);

    void handle(DeleteItemCommand command);
}
