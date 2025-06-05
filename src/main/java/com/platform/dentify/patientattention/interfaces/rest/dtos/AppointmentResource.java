package com.platform.dentify.patientattention.interfaces.rest.dtos;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public record AppointmentResource(Long id, LocalDateTime appointmentDate, String reason, Boolean completed, LocalTime duration, LocalDateTime createdAt) {

}
