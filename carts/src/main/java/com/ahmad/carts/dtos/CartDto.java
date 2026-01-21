package com.ahmad.carts.dtos;

import com.ahmad.carts.entities.enums.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {
    private Long cartId;
    private final List<CartItemDto> items = new ArrayList<>();
    private int totalItems;
    private BigDecimal totalPrice;
    private Currency currency;
}
