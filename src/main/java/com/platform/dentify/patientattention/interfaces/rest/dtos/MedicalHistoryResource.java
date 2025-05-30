package com.platform.dentify.patientattention.interfaces.rest.dtos;

import java.util.Date;

public record MedicalHistoryResource(Long id, String description, String record, String diagnosis, String medication, Date createdAt) {
}
