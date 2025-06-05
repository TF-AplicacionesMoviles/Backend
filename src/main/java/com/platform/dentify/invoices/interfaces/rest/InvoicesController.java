package com.platform.dentify.invoices.interfaces.rest;


import com.platform.dentify.invoices.domain.model.aggregates.Invoice;
import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.invoices.domain.model.queries.GetAllInvoicesByUserIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentIdQuery;
import com.platform.dentify.invoices.domain.services.InvoiceCommandService;
import com.platform.dentify.invoices.domain.services.InvoiceQueryService;
import com.platform.dentify.invoices.interfaces.rest.assemblers.CreateInvoiceCommandFromResourceAssembler;
import com.platform.dentify.invoices.interfaces.rest.assemblers.InvoiceResourceFromEntityAssembler;
import com.platform.dentify.invoices.interfaces.rest.dtos.CreateInvoiceResource;
import com.platform.dentify.invoices.interfaces.rest.dtos.InvoiceResource;
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
@RequestMapping(value = "api/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invoices", description = "Invoices Endpoint")
public class InvoicesController {

    private final InvoiceCommandService invoiceCommandService;
    private final InvoiceQueryService invoiceQueryService;

    public InvoicesController(InvoiceCommandService invoiceCommandService, InvoiceQueryService invoiceQueryService) {
        this.invoiceCommandService = invoiceCommandService;
        this.invoiceQueryService = invoiceQueryService;
    }


    @PostMapping
    @Operation(summary = "Create an invoice", description = "Create a new invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "invoice created"),
            @ApiResponse(responseCode = "400", description = "invoice not created")
    })
    public ResponseEntity<?> createAppointment(@RequestBody CreateInvoiceResource resource) {
        try {
            CreateInvoiceCommand command = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
            var result = invoiceCommandService.handle(command);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create invoice. Appointment may not exist or is not linked to the current user.");
            }

            InvoiceResource invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(result.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceResource);

        } catch (Exception E){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + E.getMessage());
        }
    }

    @GetMapping("/{appointmentId}")
    @Operation(summary = "Get an invoice by appointmentId", description = "Retrieve an invoice using the appointment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "invoice found"),
            @ApiResponse(responseCode = "404", description = "invoice not found")
    })
    public ResponseEntity<?> getInvoiceByAppointmentId(@PathVariable Long appointmentId) {
        try {
            var result = invoiceQueryService.handle(new GetInvoiceByAppointmentIdQuery(appointmentId));
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found for appointment ID: " + appointmentId);
            }

            InvoiceResource invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(result.get());
            return ResponseEntity.ok(invoiceResource);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("")
    @Operation(summary = "Get all invoices for current user", description = "Returns all invoices linked to the current user")
    public ResponseEntity<List<InvoiceResource>> getAllInvoicesForCurrentUser() {
        List<Invoice> invoices = invoiceQueryService.handle(new GetAllInvoicesByUserIdQuery());

        var resources = invoices.stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }


}
