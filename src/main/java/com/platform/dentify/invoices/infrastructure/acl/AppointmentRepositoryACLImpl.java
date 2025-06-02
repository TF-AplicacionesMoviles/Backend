package com.platform.dentify.invoices.infrastructure.acl;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.application.external.AppointmentACL;
import com.platform.dentify.invoices.application.external.ExternalAppointmentDTO;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.infrastructure.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentRepositoryACLImpl implements AppointmentACL {

    private final AppointmentRepository appointmentRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public AppointmentRepositoryACLImpl(AppointmentRepository appointmentRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.appointmentRepository = appointmentRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }


    @Override
    public Optional<ExternalAppointmentDTO> findByIdAndUserId(Long appointmentId, Long userId) {
        return appointmentRepository.findByIdAndPatient_User_Id(appointmentId, userId).map(this::mapToDTO);
    }

    @Override
    public ExternalAppointmentDTO getAppointmentById(Long appointmentId) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return findByIdAndUserId(appointmentId, userId).orElseThrow(()-> new RuntimeException("no appointment found for the current user with id: " + appointmentId));
    }

    @Override
    public void markAppointmentAsCompleted(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(()-> new RuntimeException("no appointment found for the current appointment"));
        appointment.setCompleted(true);
        appointmentRepository.save(appointment);
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
