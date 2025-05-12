package com.platform.dentify.patientattention.interfaces.rest;


import com.platform.dentify.patientattention.domain.model.commands.CreatePatientCommand;
import com.platform.dentify.patientattention.domain.services.PatientCommandService;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.CreatePatientCommandFromResourceAssembler;
import com.platform.dentify.patientattention.interfaces.rest.dtos.CreatePatientResource;
import com.platform.dentify.patientattention.interfaces.rest.dtos.PatientResource;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/patients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Patients", description = "Patients Endpoint")
public class PatientController {
    private PatientCommandService patientCommandService;

    public PatientController(PatientCommandService patientCommandService) {
        this.patientCommandService = patientCommandService;
    }


    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partition created"),
            @ApiResponse(responseCode = "400", description = "Partition not created")
    })
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientResource resource) {
        try {

            CreatePatientCommand command = CreatePatientCommandFromResourceAssembler.toCommandFromResource(resource);
            Long patientId = patientCommandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", patientId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            e.printStackTrace(); //traza completa (dev)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }

    }

}
