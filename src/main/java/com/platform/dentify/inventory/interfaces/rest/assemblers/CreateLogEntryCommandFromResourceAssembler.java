package com.platform.dentify.inventory.interfaces.rest.assemblers;

import com.platform.dentify.inventory.domain.model.commands.CreateLogEntryCommand;
import com.platform.dentify.inventory.interfaces.rest.dtos.CreateLogEntryResource;

public class CreateLogEntryCommandFromResourceAssembler {

    public static CreateLogEntryCommand toCommandFromResource(CreateLogEntryResource resource) {
        return new CreateLogEntryCommand(resource.itemId(), resource.consumedQuantity(),
                resource.invoiceId());
    }
}
