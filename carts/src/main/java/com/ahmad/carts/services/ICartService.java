package com.ahmad.carts.services;

import com.ahmad.carts.dtos.CartDto;
import com.ahmad.carts.dtos.CartItemDto;

public interface ICartService {
    CartDto addItemToCart(Long userId, CartItemDto cartItemDto);
    CartDto removeItemFromCart(Long userId, Long productId);
    CartDto updateItemQuantity(Long cartId, Long productId, int quantity);
    CartDto getCart(Long cartId);
    void deleteCart(Long cartId);

}
