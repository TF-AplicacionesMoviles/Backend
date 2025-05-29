package com.platform.dentify.inventory.application.internal.queryservices;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.inventory.domain.model.aggregates.LogEntry;
import com.platform.dentify.inventory.domain.model.queries.GetAllLogEntriesByUserIdQuery;
import com.platform.dentify.inventory.domain.model.queries.GetLogEntryByIdQuery;
import com.platform.dentify.inventory.domain.services.LogEntryQueryService;
import com.platform.dentify.inventory.infrastructure.persistence.jpa.repositories.LogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogEntryQueryServiceImpl implements LogEntryQueryService {

    private final LogEntryRepository logEntryRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    public LogEntryQueryServiceImpl(LogEntryRepository logEntryRepository,
                                    AuthenticatedUserProvider authenticatedUserProvider) {
        this.logEntryRepository = logEntryRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

//    @Override
//    public List<LogEntry> handle(GetAllLogEntriesByUserIdQuery query) {
//        Long userId = authenticatedUserProvider.getCurrentUserId();
//        return logEntryRepository.findAllByInvoice_User_Id(userId);
//    }

    @Override
    public Optional<LogEntry> handle(GetLogEntryByIdQuery query) {
        return logEntryRepository.findById(query.id());
    }
}
