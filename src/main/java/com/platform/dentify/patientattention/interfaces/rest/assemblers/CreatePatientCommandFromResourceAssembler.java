package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.patientattention.domain.model.commands.CreatePatientCommand;
import com.platform.dentify.patientattention.interfaces.rest.dtos.CreatePatientResource;

public class CreatePatientCommandFromResourceAssembler {
    public static CreatePatientCommand toCommandFromResource(CreatePatientResource resource) {
        return new CreatePatientCommand(
                resource.dni(),
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.birthday(),
                resource.homeAddress()
        );

    }
}
