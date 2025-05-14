package com.platform.dentify.invoices.domain.model.queries;

public record GetInvoiceByAppointmentQuery(Long appointmentId) {
    public GetInvoiceByAppointmentQuery {
        if(appointmentId == null || appointmentId <= 0) {
            throw new IllegalArgumentException("appointment id must be greater than 0");
        }
    }
}