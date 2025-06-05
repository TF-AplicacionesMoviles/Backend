package com.platform.dentify.patientattention.interfaces.rest.assemblers;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.interfaces.rest.dtos.AppointmentResource;

public class AppointmentResourceFromEntityAssembler {
    public static AppointmentResource toResourceFromEntity(Appointment appointment) {
        return new AppointmentResource(appointment.getId(),
                appointment.getPatient().getName().FullName(),
                appointment.getPatient().getDni().dni(),
                appointment.getAppointmentDate(),
                appointment.getReason(),
                appointment.getCompleted(),
                appointment.getDuration(),
                appointment.getCreatedAt());
    }
}
