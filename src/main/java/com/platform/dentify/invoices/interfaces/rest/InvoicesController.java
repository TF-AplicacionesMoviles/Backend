package com.platform.dentify.invoices.interfaces.rest;


import com.platform.dentify.invoices.domain.model.commands.CreateInvoiceCommand;
import com.platform.dentify.invoices.domain.services.InvoiceCommandService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "api/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invoices", description = "Invoices Endpoint")
public class InvoicesController {

    private final InvoiceCommandService invoiceCommandService;

    public InvoicesController(InvoiceCommandService invoiceCommandService) {
        this.invoiceCommandService = invoiceCommandService;
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

}
