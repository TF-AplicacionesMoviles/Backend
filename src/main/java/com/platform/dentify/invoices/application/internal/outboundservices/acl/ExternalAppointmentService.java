package com.platform.dentify.invoices.application.internal.outboundservices.acl;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.interfaces.acl.AppointmentsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalAppointmentService {
    private final AppointmentsContextFacade appointmentsContextFacade;
    public ExternalAppointmentService(AppointmentsContextFacade appointmentsContextFacade) {
        this.appointmentsContextFacade = appointmentsContextFacade;
    }
    public Optional<Appointment> fetchAppointmentById(Long id) {
        return appointmentsContextFacade.fetchAppointmentById(id);
    }
}
