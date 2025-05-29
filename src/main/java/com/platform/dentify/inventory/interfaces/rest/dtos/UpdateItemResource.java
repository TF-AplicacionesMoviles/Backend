package com.platform.dentify.inventory.interfaces.rest.dtos;

public record UpdateItemResource(String name, Double price, int stockQuantity, boolean isActive, String category) {
}
