package com.platform.dentify.iam.interfaces.rest.resources;

public record RegisterResponseResource(String accessToken, String refreshToken, String username) {
}
