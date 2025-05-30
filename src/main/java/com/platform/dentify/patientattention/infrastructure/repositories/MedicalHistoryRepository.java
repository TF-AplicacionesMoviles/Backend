package com.platform.dentify.patientattention.infrastructure.repositories;

import com.platform.dentify.patientattention.domain.model.entities.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findAllByPatientId(Long patientId);

}
