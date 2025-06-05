package com.platform.dentify.invoices.interfaces.rest.dtos;



import java.time.LocalDateTime;

public record InvoiceResource(Long id, Long appointmentId, String patientName, String dni, String email, Double amount, LocalDateTime createdAt) {
}
