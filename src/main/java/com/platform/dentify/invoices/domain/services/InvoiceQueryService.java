package com.platform.dentify.invoices.domain.services;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentIdQuery;

import java.util.Optional;

public interface InvoiceQueryService {
    Optional<Invoice> findById(GetInvoiceByAppointmentIdQuery query);
}
