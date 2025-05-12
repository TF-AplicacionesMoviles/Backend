package com.platform.dentify.patientattention.interfaces.rest.dtos;

public record PatientResource(String dni, String firstName, String lastName, String email, String birthday, String homeAddress) {
}
