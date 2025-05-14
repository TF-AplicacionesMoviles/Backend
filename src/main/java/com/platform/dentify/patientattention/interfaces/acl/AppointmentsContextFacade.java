package com.platform.dentify.patientattention.interfaces.acl;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;

import java.util.Optional;

public interface AppointmentsContextFacade {
    Optional<Appointment> fetchAppointmentById(Long id);
}
