package com.platform.dentify.patientattention.domain.model.commands;

public record DeleteAppointmentCommand(Long id) {

    public DeleteAppointmentCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number and cannot be null");
        }
    }
}
