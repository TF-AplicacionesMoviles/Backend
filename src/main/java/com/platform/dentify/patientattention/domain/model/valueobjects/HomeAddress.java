package com.platform.dentify.patientattention.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public record HomeAddress(@Size(min = 1, max = 250) String homeAddress) {
    public HomeAddress {
        if (homeAddress == null || homeAddress.isEmpty())
            throw new IllegalArgumentException("homeAddress is null or empty");
    }
}
