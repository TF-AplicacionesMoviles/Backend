package com.platform.dentify.iam.domain.services;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.domain.model.commands.UpdateInformationCommand;
import com.platform.dentify.iam.domain.model.commands.UpdatePasswordCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<User> handle(UpdateInformationCommand command);
    Optional<User> handle(UpdatePasswordCommand command);

}
