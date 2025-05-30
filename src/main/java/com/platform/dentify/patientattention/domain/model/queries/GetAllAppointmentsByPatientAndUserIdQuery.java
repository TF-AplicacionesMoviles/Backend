package com.platform.dentify.patientattention.domain.model.queries;


//appointment day, reason, completed, duration, (patient: dni)
public record GetAllAppointmentsByPatientAndUserIdQuery(Long patientId) {
    public GetAllAppointmentsByPatientAndUserIdQuery {
        if (patientId == null ) {
            throw new NullPointerException("patientId and userId cannot be null");
        }
    }
}
