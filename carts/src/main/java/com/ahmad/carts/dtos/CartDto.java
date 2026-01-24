package com.ahmad.carts.dtos;

import com.ahmad.carts.entities.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(
        name = "CartDto",
        description = "Schema that represents a cart"
)
public class CartDto {
    @Schema(
            description = "Cart ID"
    )
    private Long cartId;
    @Schema(
            description = "List of items in cart"
    )
    private final List<CartItemDto> items = new ArrayList<>();
    @Schema(
            description = "Total number of items in cart"
    )
    private int totalItems;
    @Schema(
            description = "Total price of items in cart"
    )
    private BigDecimal totalPrice;
    private Currency currency;
}
