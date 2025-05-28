package com.platform.dentify.patientattention.interfaces.rest;

import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentsByPatientAndUserIdQuery;
import com.platform.dentify.patientattention.domain.services.AppointmentQueryService;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.AppointmentResourceFromEntityAssembler;
import com.platform.dentify.patientattention.interfaces.rest.dtos.AppointmentResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appointments", description = "Appointments Endpoint")
public class AppointmentController {
    private final AppointmentQueryService appointmentQueryService;
    public AppointmentController(AppointmentQueryService appointmentQueryService) {
        this.appointmentQueryService = appointmentQueryService;
    }

    @GetMapping("/{patientId}")
    @Operation(summary = "Get all appointments of patient from the authenticated user", description = "Returns all appointments linked to an specific patient from the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No Appointments found")
    })
    public ResponseEntity<List<AppointmentResource>> getAllAppointmentsFromPatient(@PathVariable Long patientId) {
        var appointments = appointmentQueryService.handle(new GetAllAppointmentsByPatientAndUserIdQuery(patientId));

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

        var resources = appointments.stream().map(AppointmentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);

    }

}
