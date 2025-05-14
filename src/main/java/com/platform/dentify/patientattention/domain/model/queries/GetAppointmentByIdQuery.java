package com.platform.dentify.patientattention.domain.model.queries;

public record GetAppointmentByIdQuery(Long id) {
    public GetAppointmentByIdQuery {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("id is invalid");
        }
    }
}
