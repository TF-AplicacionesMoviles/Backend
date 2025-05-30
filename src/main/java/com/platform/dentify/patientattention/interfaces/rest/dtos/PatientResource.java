package com.platform.dentify.patientattention.interfaces.rest.dtos;

public record PatientResource(Long id, String dni, String firstName, String lastName, String email, String homeAddress, String birthday) {
}
