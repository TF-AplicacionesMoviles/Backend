package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.aggregates.Patient;
import com.platform.dentify.patientattention.domain.model.queries.GetAllPatientsByUserId;
import com.platform.dentify.patientattention.domain.model.queries.GetPatientByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PatientQueryService {


    List<Patient> handle();
    Optional<Patient> handle(GetPatientByIdQuery query);
}
