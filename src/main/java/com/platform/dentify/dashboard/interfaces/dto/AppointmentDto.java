package com.platform.dentify.dashboard.interfaces.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentDto
(Long id, LocalDateTime appointmentDate, String reason, LocalTime duration, LocalDateTime createdAt) {

}