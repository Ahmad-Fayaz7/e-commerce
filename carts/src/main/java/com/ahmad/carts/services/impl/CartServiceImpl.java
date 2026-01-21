package com.ahmad.carts.services.impl;

import com.ahmad.carts.dtos.CartDto;
import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.exceptions.CartNotFoundException;
import com.ahmad.carts.mapper.ICartItemMapper;
import com.ahmad.carts.entities.Cart;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.mapper.ICartMapper;
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
    private ICartMapper cartMapper;


    @Transactional
    @Override
    public CartDto addItemToCart(Long userId, CartItemDto cartItemDto) {
        // If no cart exists for the user yet, create one.
        var cart = cartRepository.findByUserId(userId).orElseGet(() -> createCart(userId));
        var existingCartItem = cart.getItemByProductId(cartItemDto.getProductId());

        if (existingCartItem.isPresent()) {
            var actualQuantity = existingCartItem.get().getQuantity();
            cart.updateQuantity(existingCartItem.get().getProductId(), actualQuantity + 1);
        } else {
            var cartItem = cartItemMapper.toEntity(cartItemDto);
            cart.addItemToCart(cartItem);
        }
        cart.calculateTotals();
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartDto removeItemFromCart(Long cartId, Long productId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found."));
        // delete item
        cart.removeItemByProductId(productId);
        return cartMapper.toDto(cartRepository.save(cart));
    }
    @Override
    public CartDto updateItemQuantity(Long cartId, Long productId, int quantity) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found."));
        cart.updateQuantity(productId, quantity);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    public CartDto getCart(Long cartId) {
        var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        return cartMapper.toDto(cart);
    }

    @Override
    public void deleteCart(Long cartId) {
        var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        cartRepository.deleteById(cart.getCartId());
    }

    private Cart createCart(Long userId) {
        return Cart.builder()
                .currency(Currency.USD)
                .userId(userId)
                .build();
    }
}
