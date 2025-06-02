package com.platform.dentify.invoices.domain.model.aggregates;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AuditableAbstractAggregateRoot<Invoice> {

    @NotNull
    @Column(nullable = false)
    private Double amount;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Invoice() {}

    public Invoice(CreateInvoiceCommand command, Appointment appointment) {
        this.amount = command.amount();
        this.appointment = appointment;
    }
}
