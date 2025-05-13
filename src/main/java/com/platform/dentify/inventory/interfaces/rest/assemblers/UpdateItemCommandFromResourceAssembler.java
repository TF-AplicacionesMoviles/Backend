package com.platform.dentify.inventory.interfaces.rest.assemblers;

import com.platform.dentify.inventory.domain.model.commands.UpdateItemCommand;
import com.platform.dentify.inventory.interfaces.rest.dtos.UpdateItemResource;

public class UpdateItemCommandFromResourceAssembler {

    public static UpdateItemCommand toCommandFromResource(UpdateItemResource resource, Long id) {
        return new UpdateItemCommand(id, resource.name(), resource.price(),
                resource.stockQuantity(), resource.isActive(), resource.category());
    }
}
