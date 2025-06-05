package com.platform.dentify.inventory.domain.model.queries;

public record GetItemByIdQuery(Long id) {

    public GetItemByIdQuery {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }

    }
}
