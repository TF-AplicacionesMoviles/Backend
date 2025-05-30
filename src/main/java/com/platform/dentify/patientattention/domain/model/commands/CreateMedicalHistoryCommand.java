package com.platform.dentify.patientattention.domain.model.commands;

public record CreateMedicalHistoryCommand(String description, String record, String diagnosis, String medication, Long patientId) {

}
