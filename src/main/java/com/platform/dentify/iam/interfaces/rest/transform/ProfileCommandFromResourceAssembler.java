package com.platform.dentify.iam.interfaces.rest.transform;

import com.platform.dentify.iam.domain.model.commands.UpdateInformationCommand;
import com.platform.dentify.iam.domain.model.commands.UpdatePasswordCommand;
import com.platform.dentify.iam.interfaces.rest.resources.UpdateInformationRequest;
import com.platform.dentify.iam.interfaces.rest.resources.UpdatePasswordRequest;

public class ProfileCommandFromResourceAssembler {
    public static UpdateInformationCommand toCommand(UpdateInformationRequest request) {
        return new UpdateInformationCommand(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.companyName()
        );
    }

    public static UpdatePasswordCommand toCommand(UpdatePasswordRequest request) {
        return new UpdatePasswordCommand(
                request.newPassword(),
                request.oldPassword()
        );
    }
}
