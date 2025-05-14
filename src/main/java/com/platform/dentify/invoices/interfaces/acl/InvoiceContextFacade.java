package com.platform.dentify.invoices.interfaces.acl;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;

import java.util.Optional;

public interface InvoiceContextFacade {
    Optional<Invoice> fetchInvoiceById(Long id);
}
