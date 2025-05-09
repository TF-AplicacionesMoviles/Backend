package com.platform.dentify.iam.interfaces.rest.transform;

import com.platform.dentify.iam.domain.model.commands.SignUpCommand;
import com.platform.dentify.iam.interfaces.rest.resources.RegisterRequestResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(RegisterRequestResource resource) {
        return new SignUpCommand(
            resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.companyName(),
                resource.username(),
                resource.password(),
                resource.trial()
        );
    }
}
