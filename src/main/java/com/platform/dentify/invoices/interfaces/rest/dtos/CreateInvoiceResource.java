package com.platform.dentify.invoices.interfaces.rest.dtos;

public record CreateInvoiceResource(Double amount, Long appointmentId) {
}
