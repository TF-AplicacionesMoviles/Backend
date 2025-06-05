package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.patientattention.domain.model.commands.UpdateAppointmentCommand;
import com.platform.dentify.patientattention.interfaces.rest.dtos.UpdateAppointmentResource;

public class UpdateAppointmentCommandFromResourceAssembler {
    public static UpdateAppointmentCommand toCommandFromResource(Long id, UpdateAppointmentResource resource) {
        return new UpdateAppointmentCommand(
                id,
                resource.appointmentDate(),
                resource.reason(),
                resource.duration()
        );
    }
}
