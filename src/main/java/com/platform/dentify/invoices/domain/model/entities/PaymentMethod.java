package com.platform.dentify.invoices.domain.model.entities;

import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.commands.CreatePaymentMethodCommand;
import com.platform.dentify.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class PaymentMethod extends AuditableModel {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Invoice> invoices;

    public PaymentMethod() {}

    public PaymentMethod(CreatePaymentMethodCommand command){
        name = command.name();
        description = command.description();

    }

}
