package com.platform.dentify.invoices.application.external;

import java.util.Optional;

public interface AppointmentACL {
    Optional<ExternalAppointmentDTO> findByIdAndUserId(Long appointmentId, Long userId);
    ExternalAppointmentDTO getAppointmentById(Long appointmentId);
    void markAppointmentAsCompleted(Long appointmentId);
}
