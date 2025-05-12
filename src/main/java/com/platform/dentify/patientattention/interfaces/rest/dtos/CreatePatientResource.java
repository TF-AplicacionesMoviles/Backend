package com.platform.dentify.patientattention.interfaces.rest.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record CreatePatientResource(String dni, String firstName, String lastName, String email, String homeAddress,
                                    @Schema(type = "date", format = "date", example = "YY-MM-DD")
                                    @JsonFormat(pattern = "yyyy-MM-dd")
                                    LocalDate birthday) {

}
