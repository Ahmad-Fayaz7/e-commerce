package com.ahmad.carts.entities;

import com.ahmad.carts.entities.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private final Long USER_ID = 1L;
    private Cart cart;
    private CartItem cartItem;

    @BeforeEach
    void setup() {
        cart = createCart();
        cartItem = createCartItem();
    }

    @Test
    void calculateTotals() {
        // given
        cart.addItemToCart(cartItem);

        // when
        cart.calculateTotals();

        // then
        assertEquals(2, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(20), cart.getTotalPrice());
    }

    @Test
    void shouldGetItemByProductId() {
        // given
        cart.addItemToCart(cartItem);

        // when
        var savedItem = cart.getItemByProductId(cartItem.getProductId());

        // then
        assertTrue(savedItem.isPresent());

    }

    @Test
    void shouldRemoveItemByProductId() {
        // given
        cart.addItemToCart(cartItem);
        assertTrue(cart.getItemByProductId(cartItem.getProductId()).isPresent());
        assertEquals(2, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(20), cart.getTotalPrice());

        // when
        cart.removeItemByProductId(cartItem.getProductId());

        // then
        assertFalse(cart.getItemByProductId(cartItem.getProductId()).isPresent());
        assertEquals(0, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(0), cart.getTotalPrice());
    }

    private CartItem createCartItem() {
        return CartItem.builder().productId(3L).quantity(2).price(BigDecimal.valueOf(10)).build();
    }

    private Cart createCart() {
        return Cart.builder().userId(USER_ID).currency(Currency.USD).build();
    }
}