package com.platform.dentify.invoices.domain.model.commands;

public record CreateInvoiceCommand(Double amount, Long appointmentId) {
    public CreateInvoiceCommand {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if (appointmentId == null || appointmentId <= 0) {
            throw new IllegalArgumentException("Appointment IDs must be greater than 0");
        }
    }
}
