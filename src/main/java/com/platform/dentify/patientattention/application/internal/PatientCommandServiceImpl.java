package com.platform.dentify.patientattention.application.internal;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.infrastructure.repositories.UserRepository;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.commands.CreatePatientCommand;
import com.platform.dentify.patientattention.domain.model.commands.DeletePatientCommand;
import com.platform.dentify.patientattention.domain.model.commands.UpdatePatientCommand;
import com.platform.dentify.patientattention.domain.services.PatientCommandService;
import com.platform.dentify.patientattention.infrastructure.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {
    private final PatientRepository patientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final UserRepository userRepository;

    public PatientCommandServiceImpl(PatientRepository patientRepository, AuthenticatedUserProvider authenticatedUserProvider, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.userRepository = userRepository;
    }

    @Override
    public Long handle(CreatePatientCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        if (patientRepository.existsByName_FirstNameAndName_LastNameAndUser_Id(command.firstName(), command.lastName(), userId)){
            throw new IllegalArgumentException("Patient already exists");
        }

        var patient = new Patient(command);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        patient.setUser(user);

        try {
            patientRepository.save(patient);
        }catch (Exception e){
            throw new IllegalStateException("Failed to save patient " + command.firstName() + " " + command.lastName(), e);
        }

        return patient.getId();

    }

    @Override
    @Transactional
    public void handle(DeletePatientCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByIdAndUser_Id(command.id(), userId);

        if(patient.isEmpty()) {
            throw new IllegalArgumentException("Patient not found");
        }

        try {
            patientRepository.deleteByIdAndUser_Id(patient.get().getId(), userId);
        } catch(RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while deleting patient" + e.getMessage());
        }
    }

    @Override
    public Optional<Patient> handle(UpdatePatientCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var patient = patientRepository.findByIdAndUser_Id(command.id(), userId);

        if(patient.isEmpty()) {
            throw new IllegalArgumentException("Patient not found");
        }

        patient.get().update(command);

        try {
            var updatedPatient = patientRepository.save(patient.get());
            return Optional.of(updatedPatient);
        } catch(RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while updating patient" + e.getMessage());
        }

    }
}
