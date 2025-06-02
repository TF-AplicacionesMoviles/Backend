package com.platform.dentify.invoices.domain.model.aggregates;

import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.invoices.domain.model.entities.PaymentMethod;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AuditableAbstractAggregateRoot<Invoice> {

    @Column(nullable = false)
    private Double amount;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "paymentmethod_id")
    private PaymentMethod paymentMethod;

    public Invoice() {}
    public Invoice(CreateInvoiceCommand command) {
        amount = command.amount();
        //appointment y paymentMethod se guardan en la implementación con "setters"
    }

}
