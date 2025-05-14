package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.queries.GetAllPatientsByUserId;
import com.platform.dentify.patientattention.domain.model.queries.GetPatientByIdQuery;
import com.platform.dentify.patientattention.domain.services.PatientQueryService;
import com.platform.dentify.patientattention.infrastructure.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientQueryServiceImpl implements PatientQueryService {

    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public PatientQueryServiceImpl(PatientRepository patientRepository,
                                   AuthenticatedUserProvider authenticatedUserProvider) {
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public List<Patient> handle() {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return patientRepository.findAllByUser_Id(userId);
    }

    @Override
    public Optional<Patient> handle(GetPatientByIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return patientRepository.findByIdAndUser_Id(query.id(), userId );

    }
}
