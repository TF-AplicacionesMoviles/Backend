package com.platform.dentify.patientattention.domain.model.commands;

import java.time.LocalDate;

public record UpdatePatientCommand(Long id,
                                   String dni,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String homeAddress,
                                   LocalDate birthday) {

    public UpdatePatientCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number and cannot be null");
        }

        if (dni == null || dni.isBlank() || dni.equalsIgnoreCase("string")) {
            throw new IllegalArgumentException("DNI cannot be blank or invalid");
        }

        if (firstName == null || firstName.isBlank() || firstName.equalsIgnoreCase("string")) {
            throw new IllegalArgumentException("First name cannot be blank or invalid");
        }

        if (lastName == null || lastName.isBlank() || lastName.equalsIgnoreCase("string")) {
            throw new IllegalArgumentException("Last name cannot be blank or invalid");
        }

        if (email == null || email.isBlank() || !email.contains("@") || email.equalsIgnoreCase("string")) {
            throw new IllegalArgumentException("Email must be valid and cannot be blank");
        }

        if (birthday == null || birthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthday must be a past date and cannot be null");
        }

        if (homeAddress == null || homeAddress.isBlank() || homeAddress.equalsIgnoreCase("string")) {
            throw new IllegalArgumentException("Home address cannot be blank or invalid");
        }
    }
}
