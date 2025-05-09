package com.platform.dentify.patientattention.domain.model.valueobjects;


import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(String firstName, String lastName) {
    public PersonName() {
        this(null, null);
    }

    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName is blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName is blank");
        }
    }

    /**
     * @return the full name of the customer
     */
    public String FullName() {
        return this.firstName + " " + this.lastName;
    }

}