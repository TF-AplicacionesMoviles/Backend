package com.platform.dentify.patientattention.domain.services;


import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.commands.CreatePatientCommand;
import com.platform.dentify.patientattention.domain.model.commands.DeletePatientCommand;
import com.platform.dentify.patientattention.domain.model.commands.UpdatePatientCommand;

import java.util.Optional;

public interface PatientCommandService {
    Long handle (CreatePatientCommand command);

    void handle(DeletePatientCommand command);

    Optional<Patient> handle(UpdatePatientCommand command);
}
