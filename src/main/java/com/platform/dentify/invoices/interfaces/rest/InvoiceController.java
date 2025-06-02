package com.platform.dentify.invoices.interfaces.rest;

import com.platform.dentify.invoices.domain.model.queries.GetAllInvoicesByUserIdQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByAppointmentQuery;
import com.platform.dentify.invoices.domain.model.queries.GetInvoiceByIdQuery;
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
@Tag(name = "Invoices", description = "Invoices API")
public class InvoiceController {
    private final InvoiceCommandService invoiceCommandService;
    private final InvoiceQueryService invoiceQueryService;

    public InvoiceController(InvoiceCommandService invoiceCommandService, InvoiceQueryService invoiceQueryService) {
        this.invoiceCommandService = invoiceCommandService;
        this.invoiceQueryService = invoiceQueryService;
    }

    @PostMapping
    @Operation(summary = "Create invoice", description = "Create invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<InvoiceResource> createInvoice(@RequestBody CreateInvoiceResource resource) {
        var createInvoiceCommand = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
        var invoice = invoiceCommandService.handle(createInvoiceCommand);

        if(invoice.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var invoiceEntity = invoice.get();
        var invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(invoiceEntity);
        return new ResponseEntity<>(invoiceResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all invoices of authenticated users", description = "Get all invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoices found"),
            @ApiResponse(responseCode = "404", description = "Invoices not found")
    })
    public ResponseEntity<List<InvoiceResource>> getAllInvoices() {
        var invoices = invoiceQueryService.handle(new GetAllInvoicesByUserIdQuery());
        if (invoices.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var invoicesResource = invoices.stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(invoicesResource);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID", description = "Get a single invoice by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    public ResponseEntity<InvoiceResource> getInvoiceById(@PathVariable Long id) {
        var invoice = invoiceQueryService.handle(new GetInvoiceByIdQuery(id));
        if(invoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(invoice.get());
        return ResponseEntity.ok(invoiceResource);
    }

    @GetMapping("/appointment/{appointmentId}")
    @Operation(summary = "Get invoice by appointment ID", description = "Get invoice associated with a specific appointment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice found"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    public ResponseEntity<InvoiceResource> getInvoiceByAppointmentId(@PathVariable Long appointmentId) {
        var invoice = invoiceQueryService.handle(new GetInvoiceByAppointmentQuery(appointmentId));
        if(invoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(invoice.get());
        return ResponseEntity.ok(invoiceResource);
    }
}
