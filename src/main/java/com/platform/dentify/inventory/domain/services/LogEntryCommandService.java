package com.platform.dentify.inventory.domain.services;

import com.platform.dentify.inventory.domain.model.commands.CreateLogEntryCommand;

public interface LogEntryCommandService {

    Long handle(CreateLogEntryCommand command);
}
