package com.platform.dentify.iam.domain.model.commands;

public record UpdatePasswordCommand(String newPassword, String oldPassword) {
    public UpdatePasswordCommand {
        if (newPassword.isEmpty()){
            throw new IllegalArgumentException("The new password cannot be empty");
        }
        if (oldPassword.isEmpty()){
            throw new IllegalArgumentException("The old password cannot be empty");
        }
    }
}
