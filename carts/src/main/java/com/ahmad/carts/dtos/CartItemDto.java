package com.ahmad.carts.dtos;

import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class CartItemDto {
    private Long productId;
    private int quantity;
    private BigDecimal price;
}
