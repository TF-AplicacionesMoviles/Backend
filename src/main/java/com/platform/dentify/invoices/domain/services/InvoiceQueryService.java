package com.platform.dentify.invoices.domain.services;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetAllInvoicesByUserIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetLast5Invoices;

import java.util.List;
import java.util.Optional;

public interface InvoiceQueryService {
    Optional<Invoice> handle(GetInvoiceByAppointmentIdQuery query);
    List<Invoice> handle(GetLast5Invoices query);
    List<Invoice> handle(GetAllInvoicesByUserIdQuery query);

}
