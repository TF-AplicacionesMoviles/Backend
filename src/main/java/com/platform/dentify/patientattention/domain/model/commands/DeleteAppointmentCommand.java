package com.platform.dentify.patientattention.domain.model.commands;

public record DeleteAppointmentCommand(Long id) {
    public DeleteAppointmentCommand {
        if (id == null) {
            throw new NullPointerException("id is null");
        }
    }
}
