package com.platform.dentify.invoices.application.internal.commandservices;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.infrastructure.repositories.UserRepository;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.application.internal.outboundservices.acl.ExternalAppointmentService;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.invoices.domain.services.InvoiceCommandService;
import com.platform.dentify.invoices.infrastructure.persistence.jpa.repositories.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {
    private final InvoiceRepository invoiceRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final ExternalAppointmentService externalAppointmentService;
    private final UserRepository userRepository;

    public InvoiceCommandServiceImpl(InvoiceRepository invoiceRepository, AuthenticatedUserProvider authenticatedUserProvider, ExternalAppointmentService externalAppointmentService,
                                     UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.externalAppointmentService = externalAppointmentService;
        this.userRepository = userRepository;
    }


    @Override
    public Long handle(CreateInvoiceCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var appointment = externalAppointmentService.fetchAppointmentById(command.appointmentId());
        if(appointment.isEmpty()) {
            throw new IllegalArgumentException("Appointment not found");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var newInvoice = new Invoice(command, appointment.get());

        newInvoice.setUser(user);

        try {
            invoiceRepository.save(newInvoice);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("An error occurred while saving invoice" + e.getMessage());
        }
        return newInvoice.getId();
    }
}
