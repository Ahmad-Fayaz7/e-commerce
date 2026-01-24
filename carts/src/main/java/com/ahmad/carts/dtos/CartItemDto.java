package com.ahmad.carts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class CartItemDto {
    @NotNull
    @Schema(
            description = "Id of a product",
            example = "10"
    )
    private Long productId;
    @Min(1)
    @Max(100)
    @NotNull
    @Schema(
            description = "Quantity of the product",
            example = "1"
    )
    private int quantity;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "Price must be zero or positive")
    @Schema(
            description = "Price of the prodcut",
            example = "150.20"
    )
    private BigDecimal price;
}
