package com.platform.dentify.iam.domain.model.commands;

//login
public record SignInCommand(String username, String password) {
    public SignInCommand {
        if (username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

    }
}
