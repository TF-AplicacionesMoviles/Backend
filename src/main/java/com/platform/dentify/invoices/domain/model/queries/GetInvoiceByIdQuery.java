package com.platform.dentify.invoices.domain.model.queries;

public record GetInvoiceByIdQuery(Long id) {
    public GetInvoiceByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id is invalid");
        }
    }
}
