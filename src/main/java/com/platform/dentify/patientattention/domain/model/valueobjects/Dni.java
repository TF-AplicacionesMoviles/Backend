package com.platform.dentify.patientattention.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record Dni(@NotBlank
                  @Column(unique = true, nullable = false)
                  @Size(min = 8, max = 8)
                  String dni) {
    public Dni {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("dni must contain at least 8 digits");
        }
    }
}
