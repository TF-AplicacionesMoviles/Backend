package com.platform.dentify.patientattention.domain.model.commands;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record CreateAppointmentCommand(LocalDateTime _appointmentDate, String _reason, Boolean _completed, LocalTime _duration) {
    public CreateAppointmentCommand {
        if (_appointmentDate == null) {
            throw new NullPointerException("appointmentDate");
        }
        if (_reason == null) {
            throw new NullPointerException("reason");
        }
        if (_completed == null) {
            throw new NullPointerException("completed");
        }
        if (_duration == null) {
            throw new NullPointerException("duration");
        }

    }
}
