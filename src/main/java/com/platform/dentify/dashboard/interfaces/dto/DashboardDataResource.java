package com.platform.dentify.dashboard.interfaces.dto;

import java.util.List;

public record DashboardDataResource(
        List<ItemDto> lowStockItems,
        List<InvoiceDto> recentPayments,
        List<AppointmentDto> recentAppointments
) {}
