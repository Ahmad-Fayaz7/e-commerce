package com.ahmad.carts.repositories;

import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartRepoistoryTest {
    @Autowired
    private ICartRepository cartRepository;
    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = createCart();
    }

    @Test
    public void shouldCreateCart() {
        // Given: a cart saved in the repository
        var saved = cartRepository.save(cart);

        // when: cart is retrieved
        Optional<Cart> found = cartRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals(Currency.USD, found.get().getCurrency());
        assertEquals(saved.getTotalItems(), found.get().getTotalItems());
    }

    @Test
    void shouldDeleteCart() {
        // Given: a cart saved in the repository
        var saved = cartRepository.save(cart);
        System.out.println(saved);
        assertTrue(cartRepository.findById(saved.getId()).isPresent());

        // When: the cart is deleted by its ID
        cartRepository.deleteById(saved.getId());

        // Then: the cart should no longer be present in the repository
        assertFalse(cartRepository.findById(saved.getId()).isPresent());
    }


    private Cart createCart() {
        var cart = new Cart();
        cart.setUserId(1L);
        cart.setItems(new ArrayList<>());
        cart.setCurrency(Currency.USD);
        cart.setTotalItems(5);
        cart.setTotalPrice(new BigDecimal(160));

        return cart;
    }
}
