package com.platform.dentify.patientattention.interfaces.rest;


import com.platform.dentify.patientattention.domain.model.commands.CreateMedicalHistoryCommand;
import com.platform.dentify.patientattention.domain.model.commands.CreatePatientCommand;
import com.platform.dentify.patientattention.domain.model.commands.DeletePatientCommand;
import com.platform.dentify.patientattention.domain.model.commands.UpdatePatientCommand;
import com.platform.dentify.patientattention.domain.model.queries.GetAllMedicalHistoriesByPatientAndUserIdQuery;
import com.platform.dentify.patientattention.domain.model.queries.GetPatientByIdQuery;
import com.platform.dentify.patientattention.domain.services.MedicalHistoryCommandService;
import com.platform.dentify.patientattention.domain.services.MedicalHistoryQueryService;
import com.platform.dentify.patientattention.domain.services.PatientCommandService;
import com.platform.dentify.patientattention.domain.services.PatientQueryService;
import com.platform.dentify.patientattention.interfaces.rest.assemblers.*;
import com.platform.dentify.patientattention.interfaces.rest.dtos.*;
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
@RequestMapping(value = "api/v1/patients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Patients", description = "Patients Endpoint")
public class PatientController {
    private final PatientCommandService patientCommandService;
    private final PatientQueryService patientQueryService;

    private final MedicalHistoryCommandService medicalHistoryCommandService;
    private final MedicalHistoryQueryService medicalHistoryQueryService;

    public PatientController(PatientCommandService patientCommandService,
                             PatientQueryService patientQueryService, MedicalHistoryCommandService medicalHistoryCommandService, MedicalHistoryQueryService medicalHistoryQueryService) {
        this.patientCommandService = patientCommandService;
        this.patientQueryService = patientQueryService;
        this.medicalHistoryCommandService = medicalHistoryCommandService;
        this.medicalHistoryQueryService = medicalHistoryQueryService;
    }


    @PostMapping
    @Operation(summary = "Create a patient", description = "Create a new patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created"),
            @ApiResponse(responseCode = "400", description = "Patient not created")
    })
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientResource resource) {
        try {

            CreatePatientCommand command = CreatePatientCommandFromResourceAssembler.toCommandFromResource(resource);
            Long patientId = patientCommandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", patientId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            //e.printStackTrace(); //traza completa (dev)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }

    }



    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID", description = "Returns a patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<PatientResource> getPatientById(@PathVariable Long id) {
        var patient = patientQueryService.handle(new GetPatientByIdQuery(id));

        if(patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var resource = PatientResourceFromEntityAssembler.toResourceFromEntity(patient.get());

        return ResponseEntity.ok(resource);
    }



    @GetMapping()
    @Operation(summary = "Get all patients of the authenticated user", description = "Returns all patients linked to the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No patients found")
    })
    public ResponseEntity<List<PatientResource>> getAllPatientsByUserId() {
        var patients = patientQueryService.handle();

        if (patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var resources = patients.stream()
                .map(PatientResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a patient",
            description = "Delete a patient using the ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted patient"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        patientCommandService.handle(new DeletePatientCommand(id));
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/{id}")
    @Operation(summary = "Update patient", description = "Update patient using the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated item"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<PatientResource> updatePatient(@PathVariable Long id,
                                                             @RequestBody UpdatePatientResource resource) {

        UpdatePatientCommand command = UpdatePatientCommandFromResourceAssembler.toCommandFromResource(resource, id);
        var patient = patientCommandService.handle(command);

        if(patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var patientResource = PatientResourceFromEntityAssembler.toResourceFromEntity(patient.get());

        return ResponseEntity.ok(patientResource);
    }


    @GetMapping("/{patientId}/medical-histories")
    @Operation(summary = "Get medical histories by patient ID", description = "Returns all medical history records for a given patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical histories found"),
            @ApiResponse(responseCode = "404", description = "No histories found for this patient")
    })
    public ResponseEntity<List<MedicalHistoryResource>> getMedicalHistories(@PathVariable Long patientId) {
        var medicalHistories = medicalHistoryQueryService.handle(new GetAllMedicalHistoriesByPatientAndUserIdQuery(patientId));

        if (medicalHistories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var resources = medicalHistories.stream().map(MedicalResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);

    }


    @PostMapping("/{patientId}/medical-histories")
    @Operation(summary = "Add a medical history to a patient", description = "Adds a medical history record to the specified patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medical history created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<?> addMedicalHistory(
            @PathVariable Long patientId,
            @RequestBody CreateMedicalHistoryResource resource
    ) {
        try {
            CreateMedicalHistoryCommand command = CreateMedicalHistoryCommandFromResourceAssembler.toCommandFromResource(patientId, resource);
            var result = medicalHistoryCommandService.handle(command);

            if (result.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create medical history");
            }

            MedicalHistoryResource medicalHistoryResource = MedicalResourceFromEntityAssembler.toResourceFromEntity(result.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(medicalHistoryResource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }

    }

}

