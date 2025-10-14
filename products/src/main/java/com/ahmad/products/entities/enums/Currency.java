package com.ahmad.products.entities.enums;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    GBP("GBP");

    private String description;
    Currency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
