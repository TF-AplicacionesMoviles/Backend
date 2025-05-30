package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.patientattention.domain.model.commands.UpdatePatientCommand;
import com.platform.dentify.patientattention.interfaces.rest.dtos.UpdatePatientResource;

public class UpdatePatientCommandFromResourceAssembler {

    public static UpdatePatientCommand toCommandFromResource(UpdatePatientResource resource, Long id) {
        return new UpdatePatientCommand(id, resource.dni(), resource.firstName(),
                resource.lastName(), resource.email(), resource.homeAddress(),
                resource.birthday());
    }
}
