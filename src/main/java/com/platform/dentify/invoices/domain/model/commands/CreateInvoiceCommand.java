package com.platform.dentify.invoices.domain.model.commands;

public record CreateInvoiceCommand(Double amount, Long appointmentId, Long paymentMethodId) {
    public CreateInvoiceCommand {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
        if (appointmentId == null) throw new IllegalArgumentException("Appointment id cannot be null");
        if (paymentMethodId == null) throw new IllegalArgumentException("Payment method id cannot be null");

    }
}
