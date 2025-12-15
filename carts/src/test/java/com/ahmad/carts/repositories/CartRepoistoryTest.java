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
        var savedCart = cartRepository.save(cart);

        // when: cart is retrieved
        Optional<Cart> cartById = cartRepository.findById(savedCart.getId());

        // then
        assertTrue(cartById.isPresent());
        assertEquals(savedCart .getId(), cartById.get().getId());
        assertEquals(Currency.USD, cartById.get().getCurrency());
        assertEquals(savedCart .getTotalItems(), cartById.get().getTotalItems());
    }

    @Test
    void shouldDeleteCart() {
        // Given: a cart saved in the repository
        var savedCart = cartRepository.save(cart);
        assertTrue(cartRepository.findById(savedCart.getId()).isPresent());

        // When: the cart is deleted by its ID
        cartRepository.deleteById(savedCart.getId());

        // Then: the cart should no longer be present in the repository
        assertFalse(cartRepository.findById(savedCart.getId()).isPresent());
    }

    @Test
    void shouldFindByUserId() {
        // given
        var savedCart = cartRepository.save(cart);
        assertTrue(cartRepository.findById(savedCart.getId()).isPresent());

        // when
        var cartByUserId = cartRepository.findByUserId(cart.getUserId());

        // then
        assertTrue(cartByUserId .isPresent());
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
