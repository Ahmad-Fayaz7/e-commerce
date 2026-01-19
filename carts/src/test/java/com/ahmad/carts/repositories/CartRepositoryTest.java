package com.ahmad.carts.repositories;

import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartRepositoryTest {
    @Autowired
    private ICartRepository cartRepository;
    private Cart cart;
    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        cart = createCart();
    }

    @Test
    public void shouldCreateCart() {
        // Given: a cart saved in the repository
        var savedCart = cartRepository.save(cart);

        // when: cart is retrieved
        Optional<Cart> cartById = cartRepository.findById(savedCart.getCartId());

        // then
        assertTrue(cartById.isPresent());
        assertEquals(savedCart .getCartId(), cartById.get().getCartId());
        assertEquals(Currency.USD, cartById.get().getCurrency());
        assertEquals(savedCart .getTotalItems(), cartById.get().getTotalItems());
    }

    @Test
    void shouldDeleteCart() {
        // Given: a cart saved in the repository
        var savedCart = cartRepository.save(cart);
        assertTrue(cartRepository.findById(savedCart.getCartId()).isPresent());

        // When: the cart is deleted by its ID
        cartRepository.deleteById(savedCart.getCartId());

        // Then: the cart should no longer be present in the repository
        assertFalse(cartRepository.findById(savedCart.getCartId()).isPresent());
    }

    @Test
    void shouldFindByUserId() {
        // given
        var savedCart = cartRepository.save(cart);
        assertTrue(cartRepository.findById(savedCart.getCartId()).isPresent());

        // when
        var cartByUserId = cartRepository.findByUserId(cart.getUserId());

        // then
        assertTrue(cartByUserId .isPresent());
    }


    private Cart createCart() {
        return Cart.builder().userId(USER_ID).currency(Currency.USD).build();
    }
}
