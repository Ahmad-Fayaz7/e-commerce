package com.ahmad.carts.repositories;

import com.ahmad.carts.entities.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartItemRepositoryTest {
    @Autowired
    private ICartItemRepository cartItemRepository;
    private CartItem item;
    @BeforeEach
    public void setUp() {
        item = createCartItem();
    }

    @Test
    public void shouldPersistCartItem() {
        var saved = cartItemRepository.save(item);
        assertNotNull(cartItemRepository.findById(saved.getId()));
    }

    @Test
    public void shouldDeleteItem() {
        var saved = cartItemRepository.save(item);
        assertNotNull(cartItemRepository.findById(saved.getId()));
        cartItemRepository.deleteById(saved.getId());
        assertFalse(cartItemRepository.findById(saved.getId()).isPresent());
    }

    private CartItem createCartItem() {
        var cartItem = new CartItem();
        cartItem.setQuantity(2);
        cartItem.setPrice(BigDecimal.valueOf(20));
        cartItem.setProductId(3L);

        return cartItem;
    }

}
