package com.platform.dentify.invoices.application.internal;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.infrastructure.repositories.UserRepository;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.invoices.domain.services.InvoiceCommandService;
import com.platform.dentify.invoices.infrastructure.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {
    private final InvoiceRepository invoiceRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final ExternalAppointmentService externalAppointmentService;
    private final UserRepository userRepository;

    public InvoiceCommandServiceImpl(InvoiceRepository invoiceRepository,
                                     AuthenticatedUserProvider authenticatedUserProvider,
                                     ExternalAppointmentService externalAppointmentService,
                                     UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.externalAppointmentService = externalAppointmentService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Invoice> handle(CreateInvoiceCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var appointment = externalAppointmentService.fetchById(command.appointmentId());

        if (appointment.isEmpty()) {
            throw new IllegalArgumentException("Appointment not found");
        }
        if (invoiceRepository.existsByAppointmentIdAndUserId(command.appointmentId(), userId)) {
            throw new IllegalArgumentException("Invoice already exists");
        }

        var invoice = new Invoice(command, appointment.get());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        invoice.setUser(user);

        try {
            invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save invoice", e);
        }
        return Optional.of(invoice);
    }
}