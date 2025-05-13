package com.platform.dentify.inventory.interfaces.rest.dtos;

public record ItemResource(Long id, String name, Double price, int stockQuantity, boolean isActive,
                           String category) {
}
