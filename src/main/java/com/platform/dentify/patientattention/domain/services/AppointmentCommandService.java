package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;

import java.util.Optional;

public interface AppointmentCommandService {
    Optional<Appointment> handle(CreateAppointmentCommand command);
}
