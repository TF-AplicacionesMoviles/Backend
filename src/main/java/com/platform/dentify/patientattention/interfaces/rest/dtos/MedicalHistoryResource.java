package com.platform.dentify.patientattention.interfaces.rest.dtos;

import java.time.LocalDateTime;

public record MedicalHistoryResource(Long id, String description, String record, String diagnosis, String medication, LocalDateTime createdAt) {
}
