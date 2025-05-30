package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.patientattention.domain.model.entities.MedicalHistory;
import com.platform.dentify.patientattention.interfaces.rest.dtos.MedicalHistoryResource;

public class MedicalResourceFromEntityAssembler {
    public static MedicalHistoryResource toResourceFromEntity(MedicalHistory entity) {
        return new MedicalHistoryResource(entity.getId(),
                entity.getDescription(),
                entity.getRecord(),
                entity.getDiagnosis().diagnosis(),
                entity.getMedication().medication(),
                entity.getCreatedAt());
    }
}
