package com.platform.dentify.invoices.interfaces.rest.dtos;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;

import java.time.LocalDateTime;
import java.util.Date;

public record InvoiceResource(Long id, Double amount, LocalDateTime createdAt) {
}
