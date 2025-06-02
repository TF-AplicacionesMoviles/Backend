package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentsByPatientAndUserIdQuery;
import com.platform.dentify.patientattention.domain.model.queries.GetAppointmentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface AppointmentQueryService {
    List<Appointment> handle(GetAllAppointmentsByPatientAndUserIdQuery query);

    Optional<Appointment> handle(GetAppointmentByIdQuery query);

    //Optional<Appointment> handle()
}
