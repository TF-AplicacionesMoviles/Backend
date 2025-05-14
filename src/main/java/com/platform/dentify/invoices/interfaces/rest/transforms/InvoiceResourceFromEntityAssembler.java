package com.platform.dentify.invoices.interfaces.rest.transforms;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.interfaces.rest.resources.InvoiceResource;

public class InvoiceResourceFromEntityAssembler {
    public static InvoiceResource toResourceFromEntity(Invoice entity) {
        return new InvoiceResource(entity.getId(), entity.getAmount(), entity.getAppointment().getId(), entity.getUser().getId());
    }
}
