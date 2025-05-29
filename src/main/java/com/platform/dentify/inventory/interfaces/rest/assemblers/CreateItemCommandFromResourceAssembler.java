package com.platform.dentify.inventory.interfaces.rest.assemblers;

import com.platform.dentify.inventory.domain.model.commands.CreateItemCommand;
import com.platform.dentify.inventory.interfaces.rest.dtos.CreateItemResource;

public class CreateItemCommandFromResourceAssembler {

    public static CreateItemCommand toCommandFromResource(CreateItemResource resource) {
        return new CreateItemCommand(resource.name(), resource.price(), resource.stockQuantity(),
                resource.category());
    }
}
