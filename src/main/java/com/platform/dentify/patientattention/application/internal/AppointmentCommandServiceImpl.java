package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.patientattention.domain.model.commands.DeleteAppointmentCommand;
import com.platform.dentify.patientattention.domain.model.commands.UpdateAppointmentCommand;
import com.platform.dentify.patientattention.domain.services.AppointmentCommandService;
import com.platform.dentify.patientattention.infrastructure.repositories.AppointmentRepository;
import com.platform.dentify.patientattention.infrastructure.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {

    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final AppointmentRepository appointmentRepository;

    public AppointmentCommandServiceImpl(PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public Optional<Appointment> handle(CreateAppointmentCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByIdAndUser_Id(command.patientId(), userId);
        if (patient.isEmpty()){
            return Optional.empty();
        }

        var appointment = new Appointment(command);
        //settea el Paciente relacionado que se encontró
        appointment.setPatient(patient.get());

        try {
            appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.of(appointment);
    }

    @Override
    @Transactional
    public Optional<Appointment> handle(UpdateAppointmentCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        // Buscar la cita y validar que pertenece a un paciente del usuario autenticado
        Appointment appointment = appointmentRepository.findByIdAndPatient_User_Id(command.id(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found or does not belong to the user"));

        appointment.setAppointmentDate(command._appointmentDate());
        appointment.setReason(command._reason());
        appointment.setDuration(command._duration());

        appointmentRepository.save(appointment);

        return Optional.of(appointment);


    }

    @Override
    @Transactional
    public void handle(DeleteAppointmentCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        boolean exists = appointmentRepository.existsByIdAndPatient_User_Id(command.id(), userId);
        if (!exists) {
            throw new IllegalArgumentException("Appointment not found or does not belong to the user");
        }

        appointmentRepository.deleteById(command.id());

    }
}
