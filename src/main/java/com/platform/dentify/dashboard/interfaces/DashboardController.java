package com.platform.dentify.dashboard.interfaces;


import com.platform.dentify.dashboard.domain.services.DashboardQueryService;
import com.platform.dentify.dashboard.interfaces.dto.DashboardDataResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {

    private final DashboardQueryService dashboardQueryService;

    public DashboardController(DashboardQueryService dashboardQueryService) {
        this.dashboardQueryService = dashboardQueryService;
    }

    @GetMapping
    public DashboardDataResource getDashboardData() {
        return new DashboardDataResource(
                dashboardQueryService.getLowStockItemsForDashboard(),
                dashboardQueryService.getRecentPayments(),
                dashboardQueryService.getRecentAppointments()
        );
    }
}