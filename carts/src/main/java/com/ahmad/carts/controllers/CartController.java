package com.ahmad.carts.controllers;

import com.ahmad.carts.dtos.CartDto;
import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.services.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController
@RequestMapping(path = {"/api/carts"}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class CartController {
    private final ICartService cartService;
    @PostMapping("/{customerId}/items")
    ResponseEntity<CartDto> addItemToCart(@PathVariable Long customerId, @RequestBody  CartItemDto cartItemDto) {
        var cartDto = cartService.addItemToCart(customerId, cartItemDto);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }

    @GetMapping("/{cartId}")
    ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    ResponseEntity<CartDto> removeItemFromCart(@PathVariable String cartId, @PathVariable  String productId) {
    var cartDto = cartService.removeItemFromCart(Long.valueOf(cartId), Long.valueOf(productId));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cartDto);
    }

    @DeleteMapping("/{cartId}")
    ResponseEntity<String> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{cartId}/items/{productId}/{quantity}")
    ResponseEntity<CartDto> updateQuantity(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable int quantity) {
        var cartDto = cartService.updateItemQuantity(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }

}
