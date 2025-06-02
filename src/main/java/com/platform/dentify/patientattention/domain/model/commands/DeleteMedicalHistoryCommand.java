package com.platform.dentify.patientattention.domain.model.commands;

public record DeleteMedicalHistoryCommand(Long id, Long patientId) {
    public DeleteMedicalHistoryCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id is not valid");
        }
        if (patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("PatientId is not valid");
        }
    }
}
