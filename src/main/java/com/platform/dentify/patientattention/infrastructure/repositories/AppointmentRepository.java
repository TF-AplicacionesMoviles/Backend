package com.platform.dentify.patientattention.infrastructure.repositories;

import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPatientId(Long patientId);
    Optional<Appointment> findByIdAndPatient_User_Id(Long appointmentId, Long userId);
    boolean existsByIdAndPatient_User_Id(Long appointmentId, Long userId);
    List<Appointment> findAllByPatient_User_IdOrderByAppointmentDateDesc(Long userId);
    List<Appointment> findAllByPatient_User_IdAndAppointmentDateBetweenOrderByAppointmentDateAsc(Long userId, LocalDateTime start, LocalDateTime end);

}
