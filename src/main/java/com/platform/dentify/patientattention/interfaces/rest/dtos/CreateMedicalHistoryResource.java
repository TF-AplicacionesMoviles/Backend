package com.platform.dentify.patientattention.interfaces.rest.dtos;

public record CreateMedicalHistoryResource(String description, String record, String diagnosis, String medication) {
}
