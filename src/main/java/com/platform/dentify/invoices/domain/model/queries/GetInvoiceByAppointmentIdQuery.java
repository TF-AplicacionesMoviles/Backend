package com.platform.dentify.invoices.domain.model.queries;

public record GetInvoiceByAppointmentIdQuery(Long appointmentId){
    public GetInvoiceByAppointmentIdQuery {
        if (appointmentId == null) throw new IllegalArgumentException("appointmentId cannot be null");
    }
}
