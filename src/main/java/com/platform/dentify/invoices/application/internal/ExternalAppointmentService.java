package com.platform.dentify.invoices.application.internal;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.interfaces.acl.AppointmentContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalAppointmentService {
    private final AppointmentContextFacade appointmentContextFacade;
    public ExternalAppointmentService(AppointmentContextFacade appointmentContextFacade) {
        this.appointmentContextFacade = appointmentContextFacade;
    }
    public Optional<Appointment> fetchById(Long id) {
        return appointmentContextFacade.fetchById(id);
    }
}
