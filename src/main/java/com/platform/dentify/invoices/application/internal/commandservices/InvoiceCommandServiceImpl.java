package com.platform.dentify.invoices.application.internal.commandservices;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.application.external.AppointmentACL;
import com.platform.dentify.invoices.application.external.ExternalAppointmentDTO;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.invoices.domain.model.entities.PaymentMethod;
import com.platform.dentify.invoices.domain.services.InvoiceCommandService;
import com.platform.dentify.invoices.infrastructure.repositories.InvoiceRepository;
import com.platform.dentify.invoices.infrastructure.repositories.PaymentMethodRepository;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final InvoiceRepository invoiceRepository;
    private final AppointmentACL appointmentACL;
    private final PaymentMethodRepository paymentMethodRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public InvoiceCommandServiceImpl(AuthenticatedUserProvider authenticatedUserProvider, InvoiceRepository invoiceRepository, AppointmentACL appointmentACL, PaymentMethodRepository paymentMethodRepository) {
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.invoiceRepository = invoiceRepository;
        this.appointmentACL = appointmentACL;
        this.paymentMethodRepository = paymentMethodRepository;
    }


    @Override
    public Optional<Invoice> handle(CreateInvoiceCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        var paymentMethod = paymentMethodRepository.findPaymentMethodById(command.paymentMethodId());

        //validar y obtener el metodo de pago
        if (paymentMethod.isEmpty()){
            return Optional.empty();
        }

        //validar si es que la cita a ligar, pertenece al usuario y a un paciente
        Optional<ExternalAppointmentDTO> appointmentOpt = appointmentACL.findByIdAndUserId(command.appointmentId(), userId);

        if (appointmentOpt.isEmpty()){
            return Optional.empty();
        }

        //valida si ya existe un pago ligado a una cita
        if (invoiceRepository.findInvoiceByAppointmentId(command.appointmentId()).isPresent()){
            return Optional.empty();
        }

        //crea el pago
        Invoice invoice = new Invoice(command);

        invoice.setPaymentMethod(paymentMethod.get());

        Appointment appointmentProxy = entityManager.getReference(Appointment.class, command.appointmentId());
        invoice.setAppointment(appointmentProxy);

        try {
            invoiceRepository.save(invoice);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.of(invoice);
    }
}
