package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentsByPatientAndUserIdQuery;
import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentUserIdOrderByAppointmentDateDescQuery;
import com.platform.dentify.patientattention.domain.model.queries.GetAppointmentByIdQuery;
import com.platform.dentify.patientattention.domain.model.queries.GetTodayAppointmentsByUserIdQuery;
import com.platform.dentify.patientattention.domain.services.AppointmentQueryService;
import com.platform.dentify.patientattention.infrastructure.repositories.AppointmentRepository;
import com.platform.dentify.patientattention.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentQueryServiceImpl implements AppointmentQueryService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public AppointmentQueryServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public List<Appointment> handle(GetAllAppointmentsByPatientAndUserIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        if (patientRepository.findById(query.patientId()).isEmpty()) {
            throw new IllegalArgumentException("Patient not found");
        }
        if (patientRepository.findByIdAndUser_Id(query.patientId(), userId).isEmpty()) {
            throw new IllegalArgumentException("Patient not found");
        }

        return appointmentRepository.findAllByPatientId(query.patientId());
    }

    @Override
    public List<Appointment> handle(GetAllAppointmentUserIdOrderByAppointmentDateDescQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return appointmentRepository.findAllByPatient_User_IdOrderByAppointmentDateDesc(userId);

    }

    @Override
    public List<Appointment> handle(GetTodayAppointmentsByUserIdQuery query) {


        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

        return appointmentRepository.findAllByPatient_User_IdAndAppointmentDateBetweenOrderByAppointmentDateAsc(query.userId(), startOfDay, endOfDay);

    }

    @Override
    public Optional<Appointment> handle(GetAppointmentByIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return appointmentRepository.findByIdAndPatient_User_Id(query.id(), userId);
    }
}
