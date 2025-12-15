package com.ahmad.carts.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartItemDto {
    private Long productId;
    private int quantity;
    private BigDecimal price;
}
