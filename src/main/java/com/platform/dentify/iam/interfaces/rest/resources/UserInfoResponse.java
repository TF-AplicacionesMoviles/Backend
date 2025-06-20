package com.platform.dentify.iam.interfaces.rest.resources;

public record UserInfoResponse(
        String fullName,
        String username,
        String email,
        String companyName
) {
}
