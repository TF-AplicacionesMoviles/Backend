package com.platform.dentify.patientattention.interfaces.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public record CreateAppointmentResource(Date appointmentDate, String reason, Boolean completed,
                                        @Schema(type = "string", format = "time", example = "HH:mm")
                                        @JsonFormat(pattern = "HH:mm")
                                        LocalTime duration, Long patientId) {
}
