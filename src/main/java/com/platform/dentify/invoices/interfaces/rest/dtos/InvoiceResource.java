package com.platform.dentify.invoices.interfaces.rest.dtos;



import java.time.LocalDateTime;

public record InvoiceResource(Long id, Double amount, LocalDateTime createdAt) {
}
