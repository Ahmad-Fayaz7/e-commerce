package com.ahmad.carts.services.impl;

import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.dtos.ICartItemMapper;
import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.repositories.ICartItemRepository;
import com.ahmad.carts.repositories.ICartRepository;
import com.ahmad.carts.services.ICartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
public class CartServiceImpl implements ICartService {

    private ICartRepository cartRepository;
    private ICartItemRepository cartItemRepository;
    private ICartItemMapper cartItemMapper;


    @Override
    public void addItemToCart(Long userId, CartItemDto cartItemDto) {
        // If no cart exists for the user yet, create one.
        var cart = cartRepository.findByUserId(userId).orElseGet(() -> createCart(userId));
        var cartItem = cartItemMapper.toEntity(cartItemDto);
        cart.addToCart(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        // delete item from database
        cartItemRepository.deleteById(cartItemId);
    }

    private Cart createCart(Long userId) {
        return Cart.builder()
                .items(new ArrayList<>())
                .totalItems(0)
                .totalPrice(BigDecimal.valueOf(0))
                .currency(Currency.USD)
                .userId(userId)
                .build();
    }
}
