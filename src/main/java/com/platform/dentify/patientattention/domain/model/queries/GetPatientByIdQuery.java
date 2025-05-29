package com.platform.dentify.patientattention.domain.model.queries;

public record GetPatientByIdQuery(Long id) {

    public GetPatientByIdQuery {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
    }
}
