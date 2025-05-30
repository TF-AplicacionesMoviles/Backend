package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.patientattention.domain.model.commands.CreateMedicalHistoryCommand;
import com.platform.dentify.patientattention.interfaces.rest.dtos.CreateMedicalHistoryResource;

public class CreateMedicalHistoryCommandFromResourceAssembler {
    public static CreateMedicalHistoryCommand toCommandFromResource(Long patientId, CreateMedicalHistoryResource resource) {
        return new CreateMedicalHistoryCommand(
                resource.description(),
                resource.record(),
                resource.diagnosis(),
                resource.medication(),
                patientId
        );
    }
}
