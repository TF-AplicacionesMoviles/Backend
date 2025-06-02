package com.platform.dentify.invoices.domain.services;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;

import java.util.Optional;

public interface InvoiceCommandService {
    Optional<Invoice> handle(CreateInvoiceCommand command);
}
