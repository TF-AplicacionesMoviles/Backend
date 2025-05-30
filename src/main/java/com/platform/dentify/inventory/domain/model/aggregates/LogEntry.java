package com.platform.dentify.inventory.domain.model.aggregates;

import com.platform.dentify.inventory.domain.model.commands.CreateLogEntryCommand;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class LogEntry extends AuditableAbstractAggregateRoot<LogEntry> {

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Min(1)
    @Column(nullable = false)
    private int consumedQuantity;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    public LogEntry() {}

    public LogEntry(CreateLogEntryCommand command) {
        this.consumedQuantity = command.consumedQuantity();
    }
}
