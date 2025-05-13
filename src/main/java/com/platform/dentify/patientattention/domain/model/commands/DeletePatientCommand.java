package com.platform.dentify.patientattention.domain.model.commands;

public record DeletePatientCommand(Long id) {

    public DeletePatientCommand {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
    }
}
