package com.platform.dentify.dashboard.interfaces.dto;

import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.patientattention.domain.model.aggregates.Appointment;

import java.util.List;

public record DashboardDataResource(List<Item> lowStockItems,
                                    List<Invoice> recentPayments,
                                    List<Appointment> todayAppointments) {
}
