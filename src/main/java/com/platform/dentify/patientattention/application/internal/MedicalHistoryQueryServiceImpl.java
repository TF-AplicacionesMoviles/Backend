package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.entities.MedicalHistory;
import com.platform.dentify.patientattention.domain.model.queries.GetAllMedicalHistoriesByPatientAndUserIdQuery;
import com.platform.dentify.patientattention.domain.services.MedicalHistoryQueryService;
import com.platform.dentify.patientattention.infrastructure.repositories.MedicalHistoryRepository;
import com.platform.dentify.patientattention.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalHistoryQueryServiceImpl implements MedicalHistoryQueryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public MedicalHistoryQueryServiceImpl(MedicalHistoryRepository medicalHistoryRepository, PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }


    @Override
    public List<MedicalHistory> handle(GetAllMedicalHistoriesByPatientAndUserIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        if (patientRepository.findById(query.patientId()).isEmpty()){
            throw new IllegalArgumentException("Patient not found");
        }
        if (patientRepository.findByIdAndUser_Id(query.patientId(), userId).isEmpty()){
            throw new IllegalArgumentException("Patient not found");
        }
        return medicalHistoryRepository.findAllByPatientId(query.patientId());
    }
}
