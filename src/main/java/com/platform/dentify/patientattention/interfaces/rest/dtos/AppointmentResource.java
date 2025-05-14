package com.platform.dentify.patientattention.interfaces.rest.dtos;

public record AppointmentResource(String reason, Boolean completed, Long patientId) {
}
