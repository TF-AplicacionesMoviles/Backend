package com.platform.dentify.inventory.application.internal.outboundservices.acl;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.interfaces.acl.InvoiceContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalInvoiceService {
    private final InvoiceContextFacade invoiceContextFacade;

    public ExternalInvoiceService(InvoiceContextFacade invoiceContextFacade) {
        this.invoiceContextFacade = invoiceContextFacade;
    }

    public Optional<Invoice> fetchInvoiceById(Long id) {
        return invoiceContextFacade.fetchInvoiceById(id);
    }
}
