package com.ahmad.carts.services;

import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.mapper.ICartItemMapper;
import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.CartItem;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.repositories.ICartRepository;
import com.ahmad.carts.services.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private ICartRepository cartRepository;
    @Mock
    private ICartItemMapper cartItemMapper;
    @InjectMocks
    private CartServiceImpl cartService;
    private final Long USER_ID = 1L;
    private CartItemDto cartItemDto;
    private CartItem cartItem;
    private Cart cart;

    @BeforeEach
    void setup() {
        cartItemDto = createCartItemDto();
        cartItem = createCartItem();
        cart = createCart();
    }

    @Test
    void shouldAddItemToCart() {
        // when
        when(cartRepository.findByUserId(USER_ID)).thenReturn(Optional.of(cart));
        when(cartItemMapper.toEntity(cartItemDto)).thenReturn(cartItem);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        cartService.addItemToCart(USER_ID,cartItemDto);

        // then
        verify(cartRepository).findByUserId(USER_ID);
        verify(cartRepository).save(cart);
    }

    @Test
    void shouldRemoveItemFromCart() {
        // given
        cart.addItemToCart(cartItem);

        // when
        when(cartRepository.findByUserId(USER_ID)).thenReturn(Optional.of(cart));
        cartService.removeItemFromCart(USER_ID, cartItemDto);

        // then
        verify(cartRepository).findByUserId(USER_ID);
        assertThat(cart.getItems()).doesNotContain(cartItem);
        verify(cartRepository).save(cart);
    }

    private CartItemDto createCartItemDto() {
        return CartItemDto.builder().productId(3L).quantity(2).price(BigDecimal.valueOf(10)).build();
    }


    private CartItem createCartItem() {
        return CartItem.builder().productId(3L).quantity(2).price(BigDecimal.valueOf(10)).build();
    }

    private Cart createCart() {
        return Cart.builder().userId(USER_ID).currency(Currency.USD).build();
    }

}
