package com.platform.dentify.invoices.interfaces.rest.transforms;

import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.invoices.interfaces.rest.resources.CreateInvoiceResource;

public class CreateInvoiceCommandFromResourceAssembler {
    public static CreateInvoiceCommand toCommandFromResource(CreateInvoiceResource resource) {
        return new CreateInvoiceCommand(resource.amount(), resource.appointmentId());
    }
}
