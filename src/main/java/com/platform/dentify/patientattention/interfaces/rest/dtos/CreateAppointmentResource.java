package com.platform.dentify.patientattention.interfaces.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record CreateAppointmentResource(LocalDateTime appointmentDate, String reason,
                                        @JsonFormat(pattern = "HH:mm") LocalTime duration, Long patientId) {
}
