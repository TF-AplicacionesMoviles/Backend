package com.platform.dentify.invoices.application.internal.queryservices;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.invoices.application.external.AppointmentACL;
import com.platform.dentify.invoices.application.external.ExternalAppointmentDTO;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetAllInvoicesByUserIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetLast5Invoices;
import com.platform.dentify.invoices.domain.services.InvoiceQueryService;
import com.platform.dentify.invoices.infrastructure.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {
    private final AppointmentACL appointmentACL;
    private final InvoiceRepository invoiceRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public InvoiceQueryServiceImpl(AppointmentACL appointmentACL, InvoiceRepository invoiceRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.appointmentACL = appointmentACL;
        this.invoiceRepository = invoiceRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public Optional<Invoice> handle(GetInvoiceByAppointmentIdQuery query) {
        Long appointmentId = query.appointmentId();

        ExternalAppointmentDTO appointmentDTO = appointmentACL.getAppointmentById(appointmentId);
        return invoiceRepository.findInvoiceByAppointmentId(appointmentDTO.id());

    }

    @Override
    public List<Invoice> handle(GetLast5Invoices query) {

        return invoiceRepository.findTop5ByAppointment_Patient_User_IdOrderByCreatedAtDesc(query.id());

    }

    @Override
    public List<Invoice> handle(GetAllInvoicesByUserIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return invoiceRepository.findByAppointment_Patient_User_Id(userId);
    }
}
