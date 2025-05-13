package com.platform.dentify.inventory.domain.services;

import com.platform.dentify.inventory.domain.model.aggregates.LogEntry;
import com.platform.dentify.inventory.domain.model.queries.GetAllLogEntriesByUserIdQuery;
import com.platform.dentify.inventory.domain.model.queries.GetLogEntryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface LogEntryQueryService {

//    List<LogEntry> handle(GetAllLogEntriesByUserIdQuery query);

    Optional<LogEntry> handle(GetLogEntryByIdQuery query);
}
