package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
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
    public Optional<Appointment> handle(UpdateAppointmentCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByIdAndUser_Id(command.patientId(), userId);
        if (patient.isEmpty()){
            throw new IllegalArgumentException("Patient not found");
        }

        var appointment = appointmentRepository.findByIdAndPatientUserId(command.id(), userId);
        if (appointment.isEmpty()){
            throw new IllegalArgumentException("Appointment not found");
        }

        appointment.get().update(command);
        appointment.get().setPatient(patient.get());

        try {
            var updatedAppointment = appointmentRepository.save(appointment.get());
            return Optional.of(updatedAppointment);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while updating appointment" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(DeleteAppointmentCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var appointment = appointmentRepository.findByIdAndPatientUserId(command.id(), userId);

        if(appointment.isEmpty()) {
            throw new IllegalArgumentException("Appointment not found");
        }

        try {
            appointmentRepository.deleteByIdAndPatientUserId(appointment.get().getId(), userId);
        } catch(RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while deleting appointment" + e.getMessage());
        }
    }
}
