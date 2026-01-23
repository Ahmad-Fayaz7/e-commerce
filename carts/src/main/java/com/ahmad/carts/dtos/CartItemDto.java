package com.ahmad.carts.dtos;

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
    private Long productId;
    @Min(1)
    @Max(100)
    private int quantity;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "Price must be zero or positive")
    private BigDecimal price;
}
