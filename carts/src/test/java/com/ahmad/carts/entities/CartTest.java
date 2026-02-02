package com.ahmad.carts.entities;

import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.exceptions.ResourceNotFoundException;
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
    void addItemToCart_shouldAddItemToCart() {
        // sanity check
        assertEquals(0, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(0), cart.getTotalPrice());

        // when
        cart.addItemToCart(cartItem);

        // then
        assertFalse(cart.getItems().isEmpty());
        assertTrue(cart.getItems().contains(cartItem));
        assertEquals(cartItem.getCart(), cart);
        assertEquals(2, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(20), cart.getTotalPrice());
    }


    @Test
    void calculateTotals_shouldCalculateTotalPriceAndTotalItems() {
        // given
        cart.addItemToCart(cartItem);

        // when
        cart.calculateTotals();

        // then
        assertEquals(2, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(20), cart.getTotalPrice());
    }

    @Test
    void calculateTotals_emptyCart_shouldReturnZeroTotals() {
        Cart cart = new Cart();

        cart.calculateTotals();

        assertEquals(0, cart.getTotalItems());
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
    }


    @Test
    void getItemByProductId_whenItemExists_shouldReturnItem() {
        // given
        cart.addItemToCart(cartItem);

        // when
        var result = cart.getItemByProductId(cartItem.getProductId());

        // then
        assertTrue(result.isPresent());
        assertEquals(3L, result.get().getProductId());
    }

    @Test
    void getItemByProductId_whenItemDoesNotExists_shouldReturnEmpty() {
        // given
        cart.addItemToCart(cartItem);

        // when
        var result = cart.getItemByProductId(99L);

        // then
        assertTrue(result.isEmpty());

    }

    @Test
    void removeItemByProductId_whenItemExists_shouldRemoveItem() {
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

    @Test
    void removeItemByProductId_whenItemDoesNotExists_shouldThrowException() {
        // when + then
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> cart.removeItemByProductId(99L)
        );

        // then
        assertEquals(
                "CartItem with productId: 99 not found.",
                exception.getMessage()
        );
    }

    @Test
    void updateQuantity_whenValid_shouldUpdateItemAndRecalculateTotals() {
        // given
        cart.addItemToCart(cartItem);

        // sanity check
        assertEquals(2, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(20), cart.getTotalPrice());

        // when
        cart.updateQuantity(3L, 5);

        // then
        assertEquals(5, cartItem.getQuantity());
        assertEquals(5, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(50), cart.getTotalPrice());
    }

    @Test
    void updateQuantity_whenQuantityLessThanOne_shouldThrowIllegalArgumentException() {
        // given
        cart.addItemToCart(cartItem);

        // sanity check
        assertEquals(2, cart.getTotalItems());
        assertEquals(BigDecimal.valueOf(20), cart.getTotalPrice());

        // when
        var exception = assertThrows(IllegalArgumentException.class, () -> cart.updateQuantity(3L, -5));

        // then
        assertEquals("Quantity cannot be less than 1", exception.getMessage());
    }


    private CartItem createCartItem() {
        return CartItem.builder().productId(3L).quantity(2).price(BigDecimal.valueOf(10)).build();
    }

    private Cart createCart() {
        return Cart.builder().userId(USER_ID).currency(Currency.USD).build();
    }
}