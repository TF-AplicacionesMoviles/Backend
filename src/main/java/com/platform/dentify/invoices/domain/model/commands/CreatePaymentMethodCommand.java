package com.platform.dentify.invoices.domain.model.commands;

public record CreatePaymentMethodCommand(String name, String description) {
    public CreatePaymentMethodCommand {
        if (name.isEmpty()){
            throw new IllegalArgumentException("name cannot be empty");
        }
        if (description.isEmpty()){
            throw new IllegalArgumentException("description cannot be empty");
        }
    }
}
