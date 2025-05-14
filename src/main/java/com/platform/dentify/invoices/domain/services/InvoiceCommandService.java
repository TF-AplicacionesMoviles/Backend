package com.platform.dentify.invoices.domain.services;

import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;

public interface InvoiceCommandService {
    Long handle(CreateInvoiceCommand command);
}
