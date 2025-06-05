package com.platform.dentify.inventory.application.internal.queryservices;

import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.inventory.domain.model.aggregates.Item;
import com.platform.dentify.inventory.domain.model.queries.FindTop3LowStockItemsByCurrentUser;
import com.platform.dentify.inventory.domain.model.queries.GetAllItemsByUserIdQuery;
import com.platform.dentify.inventory.domain.model.queries.GetItemByIdQuery;
import com.platform.dentify.inventory.domain.services.ItemQueryService;
import com.platform.dentify.inventory.infrastructure.persistence.jpa.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemRepository itemRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public ItemQueryServiceImpl(ItemRepository itemRepository,
                                AuthenticatedUserProvider authenticatedUserProvider) {
        this.itemRepository = itemRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public List<Item> handle(GetAllItemsByUserIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return itemRepository.findAllByUser_Id(userId);
    }

    @Override
    public List<Item> handle(FindTop3LowStockItemsByCurrentUser query) {

        return itemRepository.findTop3ByUser_IdAndStockQuantityLessThanOrderByStockQuantityAsc(query.userId(), 10);

    }

    @Override
    public Optional<Item> handle(GetItemByIdQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        return itemRepository.findByIdAndUser_Id(query.id(), userId);
    }
}
