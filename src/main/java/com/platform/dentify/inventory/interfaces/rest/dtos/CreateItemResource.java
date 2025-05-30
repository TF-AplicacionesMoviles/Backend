package com.platform.dentify.inventory.interfaces.rest.dtos;

public record CreateItemResource(String name, Double price, int stockQuantity, String category) {
}
