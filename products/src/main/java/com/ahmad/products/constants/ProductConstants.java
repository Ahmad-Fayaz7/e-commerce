package com.ahmad.products.constants;
public final class ProductConstants {

    private ProductConstants() {
        // Prevent instantiation
    }

    // Status codes
    public static final String STATUS_200 = "200";
    public static final String STATUS_201 = "201";
    public static final String STATUS_400 = "400";
    public static final String STATUS_404 = "404";
    public static final String STATUS_417 = "417";
    public static final String STATUS_500 = "500";

    // Messages
    public static final String MESSAGE_200 = "Request processed successfully.";
    public static final String MESSAGE_201 = "Product created successfully.";
    public static final String MESSAGE_400 = "Bad request. Please check input data.";
    public static final String MESSAGE_404 = "Product not found.";
    public static final String MESSAGE_UPDATE_417 = "Update failed, try again.";
    public static final String MESSAGE_DELETE_417 = "Delete failed, try again.";
    public static final String MESSAGE_500 = "Internal server error. Please try again later.";
}

