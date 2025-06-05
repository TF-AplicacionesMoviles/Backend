package com.platform.dentify.patientattention.domain.model.commands;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record UpdateAppointmentCommand(Long id, LocalDateTime _appointmentDate, String _reason, LocalTime _duration) {
    public UpdateAppointmentCommand {
        if (id == null) {
            throw new NullPointerException("id is null");
        }
        if (_appointmentDate == null) {
            throw new NullPointerException("appointmentDate");
        }
        if (_reason == null) {
            throw new NullPointerException("reason");
        }
        if (_duration == null) {
            throw new NullPointerException("duration");
        }
    }
}
