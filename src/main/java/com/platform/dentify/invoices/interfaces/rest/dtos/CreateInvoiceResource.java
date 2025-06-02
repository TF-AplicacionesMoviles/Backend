package com.platform.dentify.invoices.interfaces.rest.dtos;

public record CreateInvoiceResource(Double amount, Long appointmentId, Long paymentMethodId) {
    public CreateInvoiceResource {
        if (amount == null){
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (appointmentId == null){
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        /*
        if (paymentMethodId == null){
            throw new IllegalArgumentException("Payment Method cannot be null");
        }
        */

    }

}
