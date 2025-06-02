package com.platform.dentify.invoices.domain.services;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetAllInvoicesByUserIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByIdQuery;

import java.util.List;
import java.util.Optional;

public interface InvoiceQueryService {
    List<Invoice> handle(GetAllInvoicesByUserIdQuery query);
    Optional<Invoice> handle(GetInvoiceByIdQuery query);
    Optional<Invoice> handle(GetInvoiceByAppointmentQuery query);
}
