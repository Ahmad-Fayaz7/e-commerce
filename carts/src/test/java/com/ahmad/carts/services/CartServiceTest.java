package com.ahmad.carts.services;

import com.ahmad.carts.dtos.CartDto;
import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.exceptions.ResourceNotFoundException;
import com.ahmad.carts.mapper.ICartItemMapper;
import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.CartItem;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.mapper.ICartMapper;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private ICartRepository cartRepository;
    @Mock
    private ICartItemMapper cartItemMapper;
    @Mock
    private ICartMapper cartMapper;
    @InjectMocks
    private CartServiceImpl cartService;
    private final Long USER_ID = 1L;
    private CartItemDto cartItemDto;
    private CartItem cartItem;
    private Cart cart;
    private CartDto cartDto;

    @BeforeEach
    void setup() {
        cartItemDto = createCartItemDto();
        cartItem = createCartItem();
        cart = createCart();
        cartDto = createCartDto();
    }

    @Test
    void addItemToCart_whenItemAlreadyExists_shouldIncrementQuantity() {
        // given
        cart.addItemToCart(cartItem);
        when(cartRepository.findByUserId(USER_ID)).thenReturn(Optional.of(cart));
        when(cartMapper.toDto(cart)).thenReturn(cartDto);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        cartService.addItemToCart(USER_ID, cartItemDto);

        // then
        verify(cartRepository).findByUserId(USER_ID);
        verify(cartRepository).save(cart);
        assertEquals(3, cart.getTotalItems());
    }
    @Test
    void addItemToCart_whenItemDoesNotExist_shouldAddNewItem() {
        // when
        when(cartRepository.findByUserId(USER_ID)).thenReturn(Optional.of(cart));
        when(cartItemMapper.toEntity(cartItemDto)).thenReturn(cartItem);
        when(cartMapper.toDto(cart)).thenReturn(cartDto);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        cartService.addItemToCart(USER_ID,cartItemDto);

        // then
        verify(cartRepository).findByUserId(USER_ID);
        verify(cartRepository).save(cart);
        assertEquals(2, cart.getTotalItems());
    }
    @Test
    void addItemToCart_whenCartDoesNotExist_shouldCreateCart() {
        when(cartRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());
        when(cartItemMapper.toEntity(cartItemDto)).thenReturn(cartItem);
        when(cartMapper.toDto(cart)).thenReturn(cartDto);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // when
        var result = cartService.addItemToCart(USER_ID, cartItemDto);

        // then
        verify(cartRepository).findByUserId(USER_ID);
        verify(cartRepository).save(any(Cart.class));
        assertEquals(cartDto, result);
    }
    @Test
    void removeItemFromCart_whenCartDoesNotExist_shouldRemoveItem() {
        // given
        cart.addItemToCart(cartItem);

        // when
        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        cartService.removeItemFromCart(cart.getCartId(), cartItemDto.getProductId());

        // then
        verify(cartRepository).findById(cart.getCartId());
        assertThat(cart.getItems()).doesNotContain(cartItem);
        verify(cartRepository).save(cart);
    }
    @Test
    void removeItemFromCart_whenCartDoesNotExist_shouldThrowException() {
        // given
        when(cartRepository.findById(5L)).thenReturn(Optional.empty());

        // when & then
        var exception = assertThrows(ResourceNotFoundException.class,
                () -> cartService.removeItemFromCart(5L, cartItemDto.getProductId()));
        assertEquals("Cart with ID: 5 not found.", exception.getMessage());
        verify(cartRepository, never()).save(any());
    }
    @Test
    void updateItemQuantity_shouldUpdateItemQuantity() {
        cart.addItemToCart(cartItem);
        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(cartDto);

        cartService.updateItemQuantity(cart.getCartId(), cartItem.getProductId(), 5);

        assertEquals(5, cart.getTotalItems());
    }
    @Test
    void getCart_whenCartDoesNotExists_shouldThrowException() {
        when(cartRepository.findById(5L)).thenReturn(Optional.empty());
        var exception = assertThrows(ResourceNotFoundException.class, () -> cartService.getCart(5L));
        assertEquals("Cart with ID: 5 not found.", exception.getMessage());
    }
    @Test
    void getCart_whenCartExists_shouldReturnCart() {
        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        when(cartMapper.toDto(cart)).thenReturn(cartDto);
        var result = cartService.getCart(cart.getCartId());
        assertEquals(result, cartDto);
    }
    @Test
    void deleteCart_shouldDeleteCart() {
        when(cartRepository.findById(cart.getCartId())).thenReturn(Optional.of(cart));
        cartService.deleteCart(cart.getCartId());
        verify(cartRepository).findById(cart.getCartId());
        verify(cartRepository).deleteById(cart.getCartId());
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
    private CartDto createCartDto() {
        CartDto cartDto = new CartDto();
        cartDto.setCurrency(Currency.USD);
        return cartDto;
    }
}
