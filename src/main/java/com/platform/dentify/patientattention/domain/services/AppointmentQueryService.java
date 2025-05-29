package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentsByPatientAndUserIdQuery;

import java.util.List;

public interface AppointmentQueryService {
    List<Appointment> handle(GetAllAppointmentsByPatientAndUserIdQuery query);
    //Optional<Appointment> handle()
}
