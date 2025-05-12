package com.platform.dentify.patientattention.domain.model.commands;

import com.platform.dentify.iam.domain.model.aggregates.User;

import java.time.LocalDate;

public record CreatePatientCommand(String dni, String firstName, String lastName, String email, LocalDate birthday, String homeAddress) {

}
