package com.platform.dentify.dashboard.application;

import com.platform.dentify.dashboard.domain.services.DashboardQueryService;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.inventory.domain.model.queries.FindTop3LowStockItemsByCurrentUser;
import com.platform.dentify.inventory.domain.services.ItemQueryService;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.queries.GetLast5Invoices;
import com.platform.dentify.invoices.domain.services.InvoiceQueryService;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;
import com.platform.dentify.patientattention.domain.model.queries.GetTodayAppointmentsByUserIdQuery;
import com.platform.dentify.patientattention.domain.services.AppointmentQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardQueryServiceImpl implements DashboardQueryService {
    private final ItemQueryService inventoryQueryService;
    private final InvoiceQueryService invoiceQueryService;
    private final AppointmentQueryService appointmentQueryService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public DashboardQueryServiceImpl(ItemQueryService inventoryQueryService, InvoiceQueryService invoiceQueryService, AppointmentQueryService appointmentQueryService, AuthenticatedUserProvider authenticatedUserProvider) {
        this.inventoryQueryService = inventoryQueryService;
        this.invoiceQueryService = invoiceQueryService;
        this.appointmentQueryService = appointmentQueryService;
        this.authenticatedUserProvider = authenticatedUserProvider;

    }

    @Override
    public List<Item> getLowStockItemsForDashboard() {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return inventoryQueryService.handle(new FindTop3LowStockItemsByCurrentUser(userId));
    }

    @Override
    public List<Invoice> getRecentPayments() {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        return invoiceQueryService.handle(new GetLast5Invoices(userId));
    }

    @Override
    public List<Appointment> getRecentAppointments() {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return appointmentQueryService.handle(new GetTodayAppointmentsByUserIdQuery(userId));
    }
}
