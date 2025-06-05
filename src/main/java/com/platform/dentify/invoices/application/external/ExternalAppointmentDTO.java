package com.platform.dentify.invoices.application.external;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public record ExternalAppointmentDTO(
        Long id,
        LocalDateTime appointmentDate,
        String reason,
        Boolean completed,
        LocalTime duration,
        Long patientId,
        LocalDateTime createdAt
) {}
