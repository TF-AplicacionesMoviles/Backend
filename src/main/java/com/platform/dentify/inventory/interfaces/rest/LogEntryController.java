package com.platform.dentify.inventory.interfaces.rest;

import com.platform.dentify.inventory.domain.model.commands.CreateLogEntryCommand;
import com.platform.dentify.inventory.domain.model.queries.GetAllItemsByUserIdQuery;
import com.platform.dentify.inventory.domain.model.queries.GetAllLogEntriesByUserIdQuery;
import com.platform.dentify.inventory.domain.services.LogEntryCommandService;
import com.platform.dentify.inventory.domain.services.LogEntryQueryService;
import com.platform.dentify.inventory.interfaces.rest.assemblers.ItemResourceFromEntityAssembler;
import com.platform.dentify.inventory.interfaces.rest.dtos.CreateLogEntryResource;
import com.platform.dentify.inventory.interfaces.rest.dtos.ItemResource;
import com.platform.dentify.inventory.interfaces.rest.dtos.LogEntryResource;
import com.platform.dentify.inventory.interfaces.rest.assemblers.CreateLogEntryCommandFromResourceAssembler;
import com.platform.dentify.inventory.interfaces.rest.assemblers.LogEntryResourceFromEntityAssembler;
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
@RequestMapping(value = "api/v1/log-entries", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Log Entries", description = "Log Entries Endpoint")
public class LogEntryController {

    private final LogEntryCommandService logEntryCommandService;
    private final LogEntryQueryService logEntryQueryService;

    public LogEntryController(LogEntryCommandService logEntryCommandService, LogEntryQueryService logEntryQueryService) {
        this.logEntryCommandService = logEntryCommandService;
        this.logEntryQueryService = logEntryQueryService;
    }



    @PostMapping
    @Operation(summary = "Create a log entry", description = "Create a log entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created log entry"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createLogEntry(@RequestBody CreateLogEntryResource resource) {
        try {

            CreateLogEntryCommand command = CreateLogEntryCommandFromResourceAssembler.toCommandFromResource(resource);
            Long logEntryId = logEntryCommandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", logEntryId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }



//    @GetMapping
//    @Operation(summary = "Get all log entries by user ID", description = "Get all log entries by user ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Log entries found"),
//            @ApiResponse(responseCode = "404", description = "Log entries not found")
//    })
//    public ResponseEntity<List<LogEntryResource>> getAllLogEntries() {
//        var logEntries = logEntryQueryService.handle(new GetAllLogEntriesByUserIdQuery());
//
//        if (logEntries.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        var resources = logEntries.stream()
//                .map(LogEntryResourceFromEntityAssembler:: toResourceFromEntity)
//                .toList();
//
//        return ResponseEntity.ok(resources);
//    }


}
