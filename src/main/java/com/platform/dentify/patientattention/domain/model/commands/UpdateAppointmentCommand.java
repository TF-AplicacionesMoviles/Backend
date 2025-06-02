package com.platform.dentify.patientattention.domain.model.commands;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record UpdateAppointmentCommand(Long id,
                                       LocalDateTime appointmentDate,
                                       String reason,
                                       Boolean completed,
                                       LocalTime duration,
                                       Long patientId
                                       ) {

    public UpdateAppointmentCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number and cannot be null");
        }

        if (appointmentDate == null ) {
            throw new IllegalArgumentException("Appointment date cannot be null");
        }

        if (reason == null || reason.isBlank() || reason.equalsIgnoreCase("string")) {
            throw new IllegalArgumentException("Reason cannot be blank or invalid");
        }

        if (duration == null) {
            throw new IllegalArgumentException("Duration cannot be null");
        }

        if (patientId == null || patientId <= 0) {
            throw new IllegalArgumentException("Patient ID must be a positive number and cannot be null");
        }
    }
}
