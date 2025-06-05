package com.platform.dentify.dashboard.domain.services;

import com.platform.dentify.dashboard.interfaces.dto.AppointmentDto;
import com.platform.dentify.dashboard.interfaces.dto.InvoiceDto;
import com.platform.dentify.dashboard.interfaces.dto.ItemDto;

import java.util.List;

public interface DashboardQueryService {
    List<ItemDto> getLowStockItemsForDashboard();
    List<InvoiceDto> getRecentPayments();
    List<AppointmentDto> getRecentAppointments();
}
