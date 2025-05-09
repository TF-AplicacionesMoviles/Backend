package com.platform.dentify.patientattention.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public record Diagnosis(
        @Size(max = 250) String diagnosis) {
    public Diagnosis {
        if (diagnosis == null) {
            throw new NullPointerException("diagnosis");
        }
    }
}
