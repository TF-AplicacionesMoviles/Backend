package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.patientattention.domain.model.commands.DeleteAppointmentCommand;
import com.platform.dentify.patientattention.domain.model.commands.UpdateAppointmentCommand;

import java.util.Optional;

public interface AppointmentCommandService {
    Optional<Appointment> handle(CreateAppointmentCommand command);
    Optional<Appointment> handle(UpdateAppointmentCommand command);
    void handle(DeleteAppointmentCommand command);
}
