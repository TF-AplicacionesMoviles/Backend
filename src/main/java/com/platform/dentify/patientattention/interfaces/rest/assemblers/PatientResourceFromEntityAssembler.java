package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.interfaces.rest.dtos.PatientResource;

public class PatientResourceFromEntityAssembler {

    public static PatientResource toResourceFromEntity(Patient entity) {
        return new PatientResource(entity.getId(),
                entity.getDni().dni(),
                entity.getName().firstName(),
                entity.getName().lastName(),
                entity.getEmail().address(),
                entity.getHomeAddress().homeAddress(),
                entity.getBirthday().birthday().toString());
    }
}
