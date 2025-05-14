package com.platform.dentify.invoices.interfaces.rest.resources;

public record InvoiceResource(Long id, Double amount, Long appointmentId, Long userId) {
}
