package com.platform.dentify.iam.domain.model.commands;

public record UpdateInformationCommand(String firstName, String lastName, String email, String companyName) {
    public UpdateInformationCommand{
        if (firstName.isEmpty()){
            throw new IllegalArgumentException("first name cannot be empty");
        }
        if (lastName.isEmpty()){
            throw new IllegalArgumentException("last name cannot be empty");
        }
        if (email.isEmpty()){
            throw new IllegalArgumentException("email cannot be empty");
        }
        if (companyName.isEmpty()){
            throw new IllegalArgumentException("companyName cannot be empty");
        }
    }
}
