package com.platform.dentify.inventory.interfaces.rest;

import com.platform.dentify.inventory.domain.model.commands.CreateItemCommand;
import com.platform.dentify.inventory.domain.model.commands.DeleteItemCommand;
import com.platform.dentify.inventory.domain.model.commands.UpdateItemCommand;
import com.platform.dentify.inventory.domain.model.queries.GetAllItemsByUserIdQuery;
import com.platform.dentify.inventory.domain.model.queries.GetItemByIdQuery;
import com.platform.dentify.inventory.domain.services.ItemCommandService;
import com.platform.dentify.inventory.domain.services.ItemQueryService;
import com.platform.dentify.inventory.interfaces.rest.dtos.CreateItemResource;
import com.platform.dentify.inventory.interfaces.rest.dtos.ItemResource;
import com.platform.dentify.inventory.interfaces.rest.dtos.UpdateItemResource;
import com.platform.dentify.inventory.interfaces.rest.assemblers.CreateItemCommandFromResourceAssembler;
import com.platform.dentify.inventory.interfaces.rest.assemblers.ItemResourceFromEntityAssembler;
import com.platform.dentify.inventory.interfaces.rest.assemblers.UpdateItemCommandFromResourceAssembler;
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
@RequestMapping(value = "api/v1/items", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Items", description = "Items Endpoint")
public class InventoryController {

    private final ItemCommandService itemCommandService;
    private final ItemQueryService itemQueryService;

    public InventoryController(ItemCommandService itemCommandService, ItemQueryService itemQueryService) {
        this.itemCommandService = itemCommandService;
        this.itemQueryService = itemQueryService;
    }

    @PostMapping
    @Operation(summary = "Create item", description = "Create item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createItem(@RequestBody CreateItemResource resource) {
        try {

            CreateItemCommand command = CreateItemCommandFromResourceAssembler.toCommandFromResource(resource);
            Long itemId = itemCommandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", itemId));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update item", description = "Update item using the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated item"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ItemResource> updatePatient(@PathVariable Long id,
                                              @RequestBody UpdateItemResource resource) {

        UpdateItemCommand command = UpdateItemCommandFromResourceAssembler.toCommandFromResource(resource, id);
        var item = itemCommandService.handle(command);

        if(item.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var itemResource = ItemResourceFromEntityAssembler.toResourceFromEntity(item.get());

        return ResponseEntity.ok(itemResource);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an item",
            description = "Delete a item using the ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted item"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        itemCommandService.handle(new DeleteItemCommand(id));
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    @Operation(summary = "Get all items by user ID", description = "Get all items by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items found"),
            @ApiResponse(responseCode = "404", description = "Items not found")
    })
    public ResponseEntity<List<ItemResource>> getAllItems() {
        var items = itemQueryService.handle(new GetAllItemsByUserIdQuery());

        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resources = items.stream()
                .map(ItemResourceFromEntityAssembler:: toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", description = "Get item by ID if it belongs to the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<ItemResource> getItemById(@PathVariable Long id) {
        var itemOptional = itemQueryService.handle(new GetItemByIdQuery(id));

        if (itemOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var itemResource = ItemResourceFromEntityAssembler.toResourceFromEntity(itemOptional.get());
        return ResponseEntity.ok(itemResource);
    }

}
