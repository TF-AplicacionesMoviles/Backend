package com.platform.dentify.patientattention.domain.model.queries;

public record GetPatientByDniQuery(String dni) {
    public GetPatientByDniQuery {
        if (dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("DNI is null or empty");
        }
    }
}
