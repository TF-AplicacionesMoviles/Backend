package com.platform.dentify.iam.domain.services;

import com.platform.dentify.iam.domain.model.commands.SignInCommand;
import com.platform.dentify.iam.domain.model.commands.SignUpCommand;
import com.platform.dentify.iam.interfaces.rest.resources.AuthResponseResource;
import com.platform.dentify.iam.interfaces.rest.resources.RegisterResponseResource;

public interface AuthCommandService {

    AuthResponseResource login(SignInCommand command);
    RegisterResponseResource register(SignUpCommand command);

}
