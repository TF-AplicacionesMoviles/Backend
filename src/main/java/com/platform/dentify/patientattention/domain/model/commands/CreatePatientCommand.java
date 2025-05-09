package com.platform.dentify.patientattention.domain.model.commands;

import java.time.LocalDate;

public record CreatePatientCommand(String dni, String firstName, String lastName, String email, Long age, LocalDate birthday, String homeAddress) {

}
