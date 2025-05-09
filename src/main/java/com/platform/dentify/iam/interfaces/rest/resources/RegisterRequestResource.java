package com.platform.dentify.iam.interfaces.rest.resources;

public record RegisterRequestResource(String firstName, String lastName, String email, String companyName, String username, String password, Boolean trial) {
}
