package com.platform.dentify.invoices.application.internal.queryservices;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.application.external.AppointmentACL;
import com.platform.dentify.invoices.application.external.ExternalAppointmentDTO;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentIdQuery;
import com.platform.dentify.invoices.domain.services.InvoiceQueryService;
import com.platform.dentify.invoices.infrastructure.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {
    private final AppointmentACL appointmentACL;
    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryServiceImpl(AppointmentACL appointmentACL, InvoiceRepository invoiceRepository) {
        this.appointmentACL = appointmentACL;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Optional<Invoice> findById(GetInvoiceByAppointmentIdQuery query) {
        Long appointmentId = query.appointmentId();

        ExternalAppointmentDTO appointmentDTO = appointmentACL.getAppointmentById(appointmentId);
        return invoiceRepository.findInvoiceByAppointmentId(appointmentDTO.id());

    }
}
