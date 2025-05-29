package com.platform.dentify.invoices.domain.model.aggregates;

import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AuditableAbstractAggregateRoot<Invoice> {

    public Invoice() {}

}
