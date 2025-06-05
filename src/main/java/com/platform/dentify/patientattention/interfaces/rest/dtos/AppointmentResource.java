package com.platform.dentify.patientattention.interfaces.rest.dtos;

import java.time.LocalDateTime;
import java.time.LocalTime;


public record AppointmentResource(Long id, String patientName, String dni, LocalDateTime appointmentDate, String reason, Boolean completed, LocalTime duration, LocalDateTime createdAt) {

}
