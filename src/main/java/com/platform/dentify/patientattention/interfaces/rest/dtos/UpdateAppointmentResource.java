package com.platform.dentify.patientattention.interfaces.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record UpdateAppointmentResource(LocalDateTime _appointmentDate, String _reason, @JsonFormat(pattern = "HH:mm") @Schema(type = "string", format = "time", example = "HH:MM", description = "Duration in HH:mm format") LocalTime _duration) {
    public UpdateAppointmentResource {
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