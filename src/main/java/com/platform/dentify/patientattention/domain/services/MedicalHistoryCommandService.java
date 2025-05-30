package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.commands.CreateMedicalHistoryCommand;
import com.platform.dentify.patientattention.domain.model.entities.MedicalHistory;

import java.util.Optional;

public interface MedicalHistoryCommandService {
    Optional<MedicalHistory> handle(CreateMedicalHistoryCommand command);
}
