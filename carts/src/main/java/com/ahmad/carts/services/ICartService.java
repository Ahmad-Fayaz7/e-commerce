package com.ahmad.carts.services;

import com.ahmad.carts.dtos.CartItemDto;

public interface ICartService {
    void addItemToCart(Long userId, CartItemDto cartItemDto);
    void removeItemFromCart(Long cartItemId);
}
