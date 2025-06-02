package com.platform.dentify.invoices.application.internal;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetAllInvoicesByUserIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByIdQuery;
import com.platform.dentify.invoices.domain.services.InvoiceQueryService;
import com.platform.dentify.invoices.infrastructure.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {
    private final InvoiceRepository invoiceRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.invoiceRepository = invoiceRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }


    @Override
    public List<Invoice> handle(GetAllInvoicesByUserIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return invoiceRepository.findByUserId(userId);
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return invoiceRepository.findByIdAndUserId(query.id(), userId);
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByAppointmentQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return invoiceRepository.findByAppointmentIdAndUserId(query.appointmentId(), userId);
    }
}
