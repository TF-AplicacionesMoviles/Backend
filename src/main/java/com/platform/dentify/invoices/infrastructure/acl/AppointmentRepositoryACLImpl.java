package com.platform.dentify.invoices.infrastructure.acl;

import com.platform.dentify.invoices.application.external.AppointmentACL;
import com.platform.dentify.invoices.application.external.ExternalAppointmentDTO;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.infrastructure.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentRepositoryACLImpl implements AppointmentACL {

    private final AppointmentRepository appointmentRepository;

    public AppointmentRepositoryACLImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public Optional<ExternalAppointmentDTO> findByIdAndUserId(Long appointmentId, Long userId) {
        return appointmentRepository.findByIdAndPatient_User_Id(appointmentId, userId).map(this::mapToDTO);
    }

    private ExternalAppointmentDTO mapToDTO(Appointment appointment) {
        return new ExternalAppointmentDTO(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getReason(),
                appointment.getCompleted(),
                appointment.getDuration(),
                appointment.getPatient().getId(),
                appointment.getCreatedAt()
        );
    }
}
