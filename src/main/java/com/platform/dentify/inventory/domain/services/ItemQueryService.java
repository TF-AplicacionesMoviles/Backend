package com.platform.dentify.inventory.domain.services;

import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.inventory.domain.model.queries.GetAllItemsByUserIdQuery;
import com.platform.dentify.inventory.domain.model.queries.GetItemByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ItemQueryService {

    List<Item> handle(GetAllItemsByUserIdQuery query);

    Optional<Item> handle(GetItemByIdQuery query);
}
