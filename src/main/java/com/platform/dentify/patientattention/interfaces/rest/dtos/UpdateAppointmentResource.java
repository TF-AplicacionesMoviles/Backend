package com.platform.dentify.patientattention.interfaces.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record UpdateAppointmentResource(LocalDateTime appointmentDate, String reason, @JsonFormat(pattern = "HH:mm") @Schema(type = "string", format = "time", example = "HH:MM", description = "Duration in HH:mm format") LocalTime duration) {
    public UpdateAppointmentResource {
        if (appointmentDate == null) {
            throw new NullPointerException("appointmentDate");
        }
        if (reason == null) {
            throw new NullPointerException("reason");
        }
        if (duration == null) {
            throw new NullPointerException("duration");
        }
    }
}