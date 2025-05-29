package com.platform.dentify.inventory.interfaces.rest.assemblers;

import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.inventory.interfaces.rest.dtos.ItemResource;

public class ItemResourceFromEntityAssembler {

    public static ItemResource toResourceFromEntity(Item entity) {
        return new ItemResource(entity.getId(), entity.getName(), entity.getPrice(),
                entity.getStockQuantity(), entity.isActive(), entity.getCategory().name());
    }
}
