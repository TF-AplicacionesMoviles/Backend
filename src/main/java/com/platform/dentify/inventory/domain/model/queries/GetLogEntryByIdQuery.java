package com.platform.dentify.inventory.domain.model.queries;

public record GetLogEntryByIdQuery(Long id) {

    public GetLogEntryByIdQuery {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
    }
}
