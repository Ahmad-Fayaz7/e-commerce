package com.ahmad.carts.services;

import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.dtos.ICartItemMapper;
import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.CartItem;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.repositories.ICartItemRepository;
import com.ahmad.carts.repositories.ICartRepository;
import com.ahmad.carts.services.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private ICartRepository cartRepository;
    @Mock
    private ICartItemRepository cartItemRepository;
    @Mock
    private ICartItemMapper cartItemMapper;
    @InjectMocks
    private CartServiceImpl cartService;
    private final Long USER_ID = 1L;

    @Test
    void shouldAddToCart() {
        // given
        var cartItemDto = createCartItemDto();
        var cartItem = createCartItem();
        var cart = createCart();

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
        var cartItem = createCartItem();
        cartService.removeItemFromCart(cartItem.getId());
        verify(cartItemRepository).deleteById(cartItem.getId());
    }

    private CartItemDto createCartItemDto() {
        return CartItemDto.builder().productId(3L).quantity(2).price(BigDecimal.valueOf(10)).build();
    }


    private CartItem createCartItem() {
        return CartItem.builder().productId(3L).quantity(2).price(BigDecimal.valueOf(10)).build();
    }

    private Cart createCart() {
        var cart = new Cart();
        cart.setUserId(USER_ID);
        cart.setItems(new ArrayList<>());
        cart.setCurrency(Currency.USD);
        cart.setTotalItems(5);
        cart.setTotalPrice(new BigDecimal(160));

        return cart;
    }

}
