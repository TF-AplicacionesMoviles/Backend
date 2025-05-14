package com.platform.dentify.patientattention.interfaces.rest;

import com.platform.dentify.patientattention.domain.model.commands.CreateAppointmentCommand;
import com.platform.dentify.patientattention.domain.services.AppointmentCommandService;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.CreateAppointmentCommandFromResourceAssembler;
import com.platform.dentify.patientattention.interfaces.rest.dtos.CreateAppointmentResource;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appointments", description = "Appointments Endpoint")
public class AppointmentController {
    private final AppointmentCommandService appointmentCommandService;

    public AppointmentController(AppointmentCommandService appointmentCommandService) {
        this.appointmentCommandService = appointmentCommandService;
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partition created"),
            @ApiResponse(responseCode = "400", description = "Partition not created")
    })
    public ResponseEntity<?> createAppointment(@RequestBody CreateAppointmentResource resource) {
        try {

            CreateAppointmentCommand command = CreateAppointmentCommandFromResourceAssembler.toCommandFromResource(resource);
            Long appointmentId = appointmentCommandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", appointmentId));

        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            e.printStackTrace(); //traza completa (dev)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }

    }
}
