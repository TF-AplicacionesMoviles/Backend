package com.platform.dentify.patientattention.infrastructure.repositories;

import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByUser_Id(Long userId);
    //Name_FirstName → esto le indica a Spring Data JPA que navegue dentro del embebido name
    boolean existsByName_FirstNameAndName_LastNameAndUser_Id(String firstName, String lastName, Long userId);
}
