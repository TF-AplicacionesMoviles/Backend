package com.platform.dentify.patientattention.domain.services;


import com.platform.dentify.patientattention.domain.model.commands.CreatePatientCommand;

import java.util.Optional;

public interface PatientCommandService {
    Long handle (CreatePatientCommand command);
}
