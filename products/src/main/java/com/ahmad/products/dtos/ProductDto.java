package com.ahmad.products.dtos;

import com.ahmad.products.entities.enums.AvailabilityStatus;
import com.ahmad.products.entities.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Schema(
        name = "Product",
        description = "Schema to hold product info"
)
public class ProductDto {
    @Schema(description = "Product name", example = "iphone 17")
    @NotEmpty(message = "Name cannot be empty.")
    private String name;
    @Schema(description = "Product description", example = "iphone 17 released 2025")
    private String description;
    @Schema(description = "Product price", example = "1150.99")
    private BigDecimal price;
    @Schema(example = "USD")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Schema(description = "availability status", example = "IN_STOCK")
    @NotNull
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    @Schema(description = "category of product", example = "electronics")
    private String category;
    @Schema(description = "brand of product", example = "Apple")
    private String brand;
}
