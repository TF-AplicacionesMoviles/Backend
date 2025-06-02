package com.platform.dentify.invoices.interfaces.rest.dtos;

public record InvoiceResource(Long id, Double amount, Long appointmentId, Long userId) {
}
