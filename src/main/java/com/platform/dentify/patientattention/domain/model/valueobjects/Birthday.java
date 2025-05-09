package com.platform.dentify.patientattention.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public record Birthday(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthday
) {
    public Birthday {
        if (birthday == null) {
            throw new NullPointerException("birthday is null");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("birthday is after now");
        }
    }
}
