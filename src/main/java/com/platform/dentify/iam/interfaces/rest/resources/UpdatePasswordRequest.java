package com.platform.dentify.iam.interfaces.rest.resources;

public record UpdatePasswordRequest(String newPassword, String oldPassword) {
}
