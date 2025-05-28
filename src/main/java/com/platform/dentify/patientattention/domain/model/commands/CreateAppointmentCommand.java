package com.platform.dentify.patientattention.domain.model.commands;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record CreateAppointmentCommand(LocalDateTime _appointmentDate, String _reason, LocalTime _duration, Long patientId) {
    public CreateAppointmentCommand {
        if (_appointmentDate == null) {
            throw new NullPointerException("appointmentDate");
        }
        if (_reason == null) {
            throw new NullPointerException("reason");
        }
        if (_duration == null) {
            throw new NullPointerException("duration");
        }
        if (patientId == null) {
            throw new NullPointerException("patientId");
        }

    }
}
