package com.platform.dentify.inventory.interfaces.rest.assemblers;

import com.platform.dentify.inventory.domain.model.aggregates.LogEntry;
import com.platform.dentify.inventory.interfaces.rest.dtos.LogEntryResource;

public class LogEntryResourceFromEntityAssembler {

    public static LogEntryResource toResourceFromEntity(LogEntry entity) {
        return new LogEntryResource(entity.getId(), entity.getItem().getId(), entity.getConsumedQuantity(),
                entity.getInvoice().getId());
    }
}
