package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;

public interface AppointmentCommandService {
    Long handle(CreateAppointmentCommand command);
}
