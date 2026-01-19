package com.ahmad.carts.services.impl;

import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.exceptions.CartNotFoundException;
import com.ahmad.carts.mapper.ICartItemMapper;
import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.repositories.ICartRepository;
import com.ahmad.carts.services.ICartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartServiceImpl implements ICartService {

    private ICartRepository cartRepository;
    private ICartItemMapper cartItemMapper;


    @Transactional
    @Override
    public void addItemToCart(Long userId, CartItemDto cartItemDto) {
        // If no cart exists for the user yet, create one.
        var cart = cartRepository.findByUserId(userId).orElseGet(() -> createCart(userId));
        var existingCartItem = cart.getItemByProductId(cartItemDto.getProductId());

        if (existingCartItem.isPresent()) {
            var actualQuantity = existingCartItem.get().getQuantity();
            cart.updateQuantity(existingCartItem.get().getProductId(), actualQuantity);
        } else {
            var cartItem = cartItemMapper.toEntity(cartItemDto);
            cart.addItemToCart(cartItem);
        }
        cart.calculateTotals();
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long userId, CartItemDto cartItemDto) {
        var cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found."));
        // delete item
        cart.removeItemByProductId(cartItemDto.getProductId());
        cartRepository.save(cart);
    }

    private Cart createCart(Long userId) {
        return Cart.builder()
                .currency(Currency.USD)
                .userId(userId)
                .build();
    }
}
