package com.platform.dentify.patientattention.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Medication(String medication) {
    public Medication{
        if (medication == null || medication.isEmpty()){
            throw new IllegalArgumentException("medication is null or empty");
        }
    }
}
