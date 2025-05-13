package com.platform.dentify.inventory.application.internal.commandservices;

import com.platform.dentify.inventory.domain.model.aggregates.LogEntry;
import com.platform.dentify.inventory.domain.model.commands.CreateLogEntryCommand;
import com.platform.dentify.inventory.domain.model.commands.UpdateItemCommand;
import com.platform.dentify.inventory.domain.services.ItemCommandService;
import com.platform.dentify.inventory.domain.services.LogEntryCommandService;
import com.platform.dentify.inventory.infrastructure.persistence.jpa.repositories.ItemRepository;
import com.platform.dentify.inventory.infrastructure.persistence.jpa.repositories.LogEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class LogEntryCommandServiceImpl implements LogEntryCommandService {

    private final LogEntryRepository logEntryRepository;
    private final ItemRepository itemRepository;
    private final ItemCommandService itemCommandService;

    public LogEntryCommandServiceImpl(LogEntryRepository logEntryRepository,
                                      ItemRepository itemRepository,
                                      ItemCommandService itemCommandService) {
        this.logEntryRepository = logEntryRepository;
        this.itemRepository = itemRepository;
        this.itemCommandService = itemCommandService;
    }

    @Override
    public Long handle(CreateLogEntryCommand command) {
        var item = itemRepository.findById(command.itemId());

        if(item.isEmpty()) {
            throw new IllegalArgumentException("Item not found");
        }

//        var invoice = fetchById(command.invoiceId());
//
//        if(invoice.isEmpty()) {
//            throw new IllegalArgumentException("Invoice not found");
//        }

        var logEntry = new LogEntry(command);

        logEntry.setItem(item.get());
//        logEntry.setInvoice(invoice.get());

        if(command.consumedQuantity() > item.get().getStockQuantity()) {
            throw new IllegalArgumentException("Quantity exceeded");
        }

        itemCommandService.handle(new UpdateItemCommand(item.get().getId(),
                item.get().getName(),
                item.get().getPrice(),
                item.get().getStockQuantity() - command.consumedQuantity(),
                item.get().isActive(),
                item.get().getCategory().name()));

        try {
            logEntryRepository.save(logEntry);
        } catch(RuntimeException exception) {
            throw new IllegalArgumentException("An error occurred while saving log entry" + exception.getMessage());
        }

        return logEntry.getId();
    }

}
