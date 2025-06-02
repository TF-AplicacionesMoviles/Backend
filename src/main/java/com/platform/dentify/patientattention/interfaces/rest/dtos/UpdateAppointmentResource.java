package com.platform.dentify.patientattention.interfaces.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record UpdateAppointmentResource(LocalDateTime appointmentDate, String reason, Boolean completed,
                                        @JsonFormat(pattern = "HH:mm") LocalTime duration, Long patientId) {
}
