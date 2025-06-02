package com.platform.dentify.patientattention.domain.model.queries;

public record GetAppointmentByIdQuery(Long appointmentId) {
    public GetAppointmentByIdQuery {
        if (appointmentId == null || appointmentId <= 0) {
            throw new IllegalArgumentException("appointmentId is null or empty");
        }
    }
}
