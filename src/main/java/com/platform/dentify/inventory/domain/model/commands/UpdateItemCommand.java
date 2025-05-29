package com.platform.dentify.inventory.domain.model.commands;


public record UpdateItemCommand(Long id, String name, Double price, int stockQuantity, boolean isActive, String category) {

    public UpdateItemCommand {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }

        if(name == null || name.isBlank() || name.equals("string")) {
            throw new IllegalArgumentException("Name cannot be blank or null");
        }

        if(price == null || price <= 0) {
            throw new IllegalArgumentException("Price cannot be null or zero");
        }

        if(stockQuantity <= 0) {
            throw new IllegalArgumentException("Stock quantity cannot be less than 1");
        }

        if(category == null || category.isBlank() || category.equals("string")) {
            throw new IllegalArgumentException("Category cannot be blank or invalid");
        }

    }

}
