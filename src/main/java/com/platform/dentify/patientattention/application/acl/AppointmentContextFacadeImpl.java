package com.platform.dentify.patientattention.application.acl;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.queries.GetAppointmentByIdQuery;
import com.platform.dentify.patientattention.domain.services.AppointmentQueryService;
import com.platform.dentify.patientattention.interfaces.acl.AppointmentsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentContextFacadeImpl implements AppointmentsContextFacade {
    private final AppointmentQueryService appointmentQueryService;

    public AppointmentContextFacadeImpl(AppointmentQueryService appointmentQueryService) {
        this.appointmentQueryService = appointmentQueryService;
    }

    @Override
    public Optional<Appointment> fetchAppointmentById(Long id) {
        var getAppointmentById = new GetAppointmentByIdQuery(id);
        return appointmentQueryService.handle(getAppointmentById);
    }
}
