package com.platform.dentify.invoices.interfaces.rest.dtos;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;

import java.util.Date;

public record InvoiceResource(Long id, Double amount, Date createdAt) {
}
