package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.patientattention.domain.services.AppointmentCommandService;
import com.platform.dentify.patientattention.infrastructure.repositories.AppointmentRepository;
import com.platform.dentify.patientattention.infrastructure.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentCommandServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Long handle(CreateAppointmentCommand command) {

        var appointment = new Appointment(command);

        Patient patientId = patientRepository.findById(command._patientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        System.out.println(patientId.getId() + " " + command._patientId());

        appointment.setPatient(patientId);

        try {
            appointmentRepository.save(appointment);
            System.out.println("Cita guardada con ID: " + appointment.getId());
        } catch (Exception e) {
            System.out.println("Error al guardar la cita: " + e.getMessage());
            e.printStackTrace();
            throw e;        }

        return appointment.getId();
    }
}
