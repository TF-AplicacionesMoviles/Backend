package com.platform.dentify.patientattention.interfaces.rest;

import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentsByPatientAndUserIdQuery;
import com.platform.dentify.patientattention.domain.services.AppointmentCommandService;
import com.platform.dentify.patientattention.domain.services.AppointmentQueryService;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.AppointmentResourceFromEntityAssembler;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.CreateAppointmentCommandFromResourceAssembler;
import com.platform.dentify.patientattention.interfaces.rest.dtos.AppointmentResource;
import com.platform.dentify.patientattention.interfaces.rest.dtos.CreateAppointmentResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appointments", description = "Appointments Endpoint")
public class AppointmentController {
    private final AppointmentQueryService appointmentQueryService;
    private final AppointmentCommandService appointmentCommandService;
    public AppointmentController(AppointmentQueryService appointmentQueryService, AppointmentCommandService appointmentCommandService) {
        this.appointmentQueryService = appointmentQueryService;
        this.appointmentCommandService = appointmentCommandService;
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

    @PostMapping
    @Operation(summary = "Create an appointment", description = "Create a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "appointment created"),
            @ApiResponse(responseCode = "400", description = "appointment not created")
    })
    public ResponseEntity<?> createAppointment(@RequestBody CreateAppointmentResource resource){
        try {
            CreateAppointmentCommand command = CreateAppointmentCommandFromResourceAssembler.toCommandFromResource(resource);
            var result = appointmentCommandService.handle(command);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create appointment. Patient may not exist or is not linked to the current user.");
            }

            AppointmentResource appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(result.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(appointmentResource);

        }catch (Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + E.getMessage());

        }
    }

}
