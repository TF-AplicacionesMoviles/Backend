package com.platform.dentify.patientattention.domain.services;

import com.platform.dentify.patientattention.domain.model.entities.MedicalHistory;
import com.platform.dentify.patientattention.domain.model.queries.GetAllMedicalHistoriesByPatientAndUserIdQuery;

import java.util.List;

public interface MedicalHistoryQueryService {
    List<MedicalHistory> handle(GetAllMedicalHistoriesByPatientAndUserIdQuery query);
}
