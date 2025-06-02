package com.platform.dentify.patientattention.interfaces.acl;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;

import java.util.Optional;

public interface AppointmentContextFacade {
    Optional<Appointment> fetchById(Long id);
}