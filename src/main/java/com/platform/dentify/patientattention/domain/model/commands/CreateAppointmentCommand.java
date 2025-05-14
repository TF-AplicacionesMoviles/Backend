package com.platform.dentify.patientattention.domain.model.commands;

import com.platform.dentify.patientattention.domain.model.aggregates.Patient;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public record CreateAppointmentCommand(Date _appointmentDate, String _reason, Boolean _completed, LocalTime _duration, Long _patientId) {
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
        if (_patientId == null || _patientId <= 0) {
            throw new NullPointerException("patientId");
        }
    }
}
