package com.platform.dentify.invoices.application.internal.queryservices;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetAllInvoiceQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByIdQuery;
import com.platform.dentify.invoices.domain.services.InvoiceQueryService;
import com.platform.dentify.invoices.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {
    private final InvoiceRepository invoiceRepository;
    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> handle(GetAllInvoiceQuery query) {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByIdQuery query) {
        return invoiceRepository.findById(query.id());
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByAppointmentQuery query) {
        return invoiceRepository.findInvoiceByAppointmentId(query.appointmentId());
    }
}