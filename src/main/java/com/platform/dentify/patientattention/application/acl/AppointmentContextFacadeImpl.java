package com.platform.dentify.patientattention.application.acl;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.queries.GetAppointmentByIdQuery;
import com.platform.dentify.patientattention.domain.services.AppointmentQueryService;
import com.platform.dentify.patientattention.interfaces.acl.AppointmentContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentContextFacadeImpl implements AppointmentContextFacade {
    private final AppointmentQueryService appointmentQueryService;

    public AppointmentContextFacadeImpl(AppointmentQueryService appointmentQueryService) {
        this.appointmentQueryService = appointmentQueryService;
    }

    @Override
    public Optional<Appointment> fetchById(Long id) {
        var getByIdAndUserId = new GetAppointmentByIdQuery(id);
        return appointmentQueryService.handle(getByIdAndUserId);
    }
}
