package com.platform.dentify.iam.interfaces.rest.transform;

import com.platform.dentify.iam.domain.model.commands.SignInCommand;
import com.platform.dentify.iam.interfaces.rest.resources.AuthRequestResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(AuthRequestResource resource) {
        return new SignInCommand(resource.username(), resource.password());
    }
}   
