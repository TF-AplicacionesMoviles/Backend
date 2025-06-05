package com.platform.dentify.dashboard.domain.services;

import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;

import java.util.List;

public interface DashboardQueryService {
    List<Item> getLowStockItemsForDashboard();
    List<Invoice> getRecentPayments();
    List<Appointment> getRecentAppointments();
}
