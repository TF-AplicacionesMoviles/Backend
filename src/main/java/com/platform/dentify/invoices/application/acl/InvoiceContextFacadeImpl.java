package com.platform.dentify.invoices.application.acl;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByIdQuery;
import com.platform.dentify.invoices.domain.services.InvoiceQueryService;
import com.platform.dentify.invoices.interfaces.acl.InvoiceContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceContextFacadeImpl implements InvoiceContextFacade {
    private final InvoiceQueryService invoiceQueryService;

    public InvoiceContextFacadeImpl(InvoiceQueryService invoiceQueryService) {
        this.invoiceQueryService = invoiceQueryService;
    }

    @Override
    public Optional<Invoice> fetchById(Long id) {
        var getByIdAndUserId = new GetInvoiceByIdQuery(id);
        return invoiceQueryService.handle(getByIdAndUserId);
    }
}
