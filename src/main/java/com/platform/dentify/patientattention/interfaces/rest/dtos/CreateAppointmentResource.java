package com.platform.dentify.patientattention.interfaces.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record CreateAppointmentResource(LocalDateTime appointmentDate, String reason,
                                        @JsonFormat(pattern = "HH:mm") @Schema(type = "string", format = "time", example = "HH:MM", description = "Duration in HH:mm format") LocalTime duration, Long patientId) {
}
