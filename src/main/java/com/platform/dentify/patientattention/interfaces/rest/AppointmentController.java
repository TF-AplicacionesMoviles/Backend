package com.platform.dentify.patientattention.interfaces.rest;

import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.patientattention.domain.model.commands.DeleteAppointmentCommand;
import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentsByPatientAndUserIdQuery;
import com.platform.dentify.patientattention.domain.model.queries.GetAllAppointmentUserIdOrderByAppointmentDateDescQuery;
import com.platform.dentify.patientattention.domain.model.queries.GetAppointmentByIdQuery;
import com.platform.dentify.patientattention.domain.services.AppointmentCommandService;
import com.platform.dentify.patientattention.domain.services.AppointmentQueryService;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.AppointmentResourceFromEntityAssembler;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.CreateAppointmentCommandFromResourceAssembler;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.UpdateAppointmentCommandFromResourceAssembler;
import com.platform.dentify.patientattention.interfaces.rest.dtos.AppointmentResource;
import com.platform.dentify.patientattention.interfaces.rest.dtos.CreateAppointmentResource;
import com.platform.dentify.patientattention.interfaces.rest.dtos.UpdateAppointmentResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Operation(
            summary = "Get appointment by id of the authenticated user",
            description = "Returns an specific appointment"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No appointment found for the user")
    })
    @GetMapping("/appointment/{id}")
    public ResponseEntity<AppointmentResource> getAppointmentById(@PathVariable Long id) {
        var result = appointmentQueryService.handle(new GetAppointmentByIdQuery(id));
        return result.map(appointment ->
                ResponseEntity.ok(AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(
            summary = "Get all appointments of the authenticated user",
            description = "Returns all appointments of all patients belonging to the current user, ordered by date descending"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No appointments found for the user")
    })
    public ResponseEntity<List<AppointmentResource>> getAllAppointmentsByUser() {
        var appointments = appointmentQueryService.handle(new GetAllAppointmentUserIdOrderByAppointmentDateDescQuery());

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var resources = appointments.stream()
                .map(AppointmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
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

    @PutMapping("/{appointmentId}")
    @Operation(summary = "Update an existing appointment", description = "Updates the details of an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<?> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody UpdateAppointmentResource resource) {
        try {
            var command = UpdateAppointmentCommandFromResourceAssembler.toCommandFromResource(appointmentId, resource);
            var result = appointmentCommandService.handle(command);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found or does not belong to user");
            }

            AppointmentResource updatedResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(result.get());
            return ResponseEntity.ok(updatedResource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{appointmentId}")
    @Operation(summary = "Delete an appointment", description = "Deletes a specific appointment belonging to the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found or not authorized")
    })
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId) {
        try {
            appointmentCommandService.handle(new DeleteAppointmentCommand(appointmentId));
            return ResponseEntity.ok(Map.of("message", "Appointment deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred"));

        }
    }




}
