package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.commands.CreateMedicalHistoryCommand;
import com.platform.dentify.patientattention.domain.model.entities.MedicalHistory;
import com.platform.dentify.patientattention.domain.services.MedicalHistoryCommandService;
import com.platform.dentify.patientattention.infrastructure.repositories.MedicalHistoryRepository;
import com.platform.dentify.patientattention.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalHistoryCommandServiceImpl implements MedicalHistoryCommandService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    public MedicalHistoryCommandServiceImpl(MedicalHistoryRepository medicalHistoryRepository, PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public Optional<MedicalHistory> handle(CreateMedicalHistoryCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByIdAndUser_Id(command.patientId(), userId);


        if (patient.isEmpty()){
            return Optional.empty();
        }

        var medicalHistory = new MedicalHistory(command);
        medicalHistory.setPatient(patient.get());

        try {
            medicalHistoryRepository.save(medicalHistory);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.of(medicalHistory);
    }
}
