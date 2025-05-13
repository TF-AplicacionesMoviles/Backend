package com.platform.dentify.inventory.domain.model.aggregates;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.inventory.domain.model.commands.CreateItemCommand;
import com.platform.dentify.inventory.domain.model.commands.UpdateItemCommand;
import com.platform.dentify.inventory.domain.model.valueobjects.ItemCategory;
import com.platform.dentify.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Item extends AuditableAbstractAggregateRoot<Item> {

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Min(0)
    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private ItemCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogEntry> logEntries;

    public Item() {}

    public Item(CreateItemCommand command) {
        this.name = command.name();
        this.price = command.price();
        this.stockQuantity = command.stockQuantity();
        this.category = ItemCategory.valueOf(command.category().toUpperCase());
        this.isActive = true;
    }

    public void update(UpdateItemCommand command) {
        this.name = command.name();
        this.price = command.price();
        this.stockQuantity = command.stockQuantity();
        this.category = ItemCategory.valueOf(command.category().toUpperCase());
        this.isActive = command.isActive();
    }

    public Item updateStockQuantity(int consumedQuantity) {
        this.stockQuantity = stockQuantity - consumedQuantity;
        return this;
    }
}
