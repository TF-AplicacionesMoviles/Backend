package com.platform.dentify.invoices.interfaces.rest.assemblers;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.interfaces.rest.dtos.InvoiceResource;

public class InvoiceResourceFromEntityAssembler {
    public static InvoiceResource toResourceFromEntity(Invoice invoice) {
        return new InvoiceResource(invoice.getId(),
                invoice.getAppointment().getId(),
                invoice.getAppointment().getPatient().getName().FullName(),
                invoice.getAppointment().getPatient().getDni().dni(),
                invoice.getAppointment().getPatient().getEmail().address()
                ,invoice.getAmount(), invoice.getCreatedAt());
    }
}
