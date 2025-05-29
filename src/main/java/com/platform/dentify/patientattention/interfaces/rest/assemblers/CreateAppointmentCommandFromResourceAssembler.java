package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.patientattention.interfaces.rest.dtos.CreateAppointmentResource;

public class CreateAppointmentCommandFromResourceAssembler {
    public static CreateAppointmentCommand toCommandFromResource(CreateAppointmentResource resource) {
        return new CreateAppointmentCommand(
                resource.appointmentDate(),
                resource.reason(),
                resource.duration(),
                resource.patientId()
        );
    }
}
