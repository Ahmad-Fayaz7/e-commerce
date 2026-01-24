package com.ahmad.carts.controllers;

import com.ahmad.carts.dtos.CartDto;
import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.dtos.ErrorResponseDto;
import com.ahmad.carts.services.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Carts in e-commerce",
        description = "CRUD REST APIs in e-commerce to CREATE, UPDATE, FETCH AND DELETE cart details"
)
@AllArgsConstructor
@RestController
@RequestMapping(path = {"/api/carts"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CartController {
    private final ICartService cartService;
    @Operation(
            summary = "Add item to cart",
            description = "API to add an item to cart"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/{customerId}/items")
    ResponseEntity<CartDto> addItemToCart(@PathVariable Long customerId, @Valid @RequestBody  CartItemDto cartItemDto) {
        var cartDto = cartService.addItemToCart(customerId, cartItemDto);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }
    @Operation(
            summary = "Fetch cart",
            description = "API to fetch a cart"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{cartId}")
    ResponseEntity<CartDto> getCart(@PathVariable Long cartId) {
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }
    @Operation(
            summary = "Remove an item from cart",
            description = "API to remove an item from cart"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO CONTENT"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{cartId}/items/{productId}")
    ResponseEntity<CartDto> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
    var cartDto = cartService.removeItemFromCart(cartId, productId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cartDto);
    }
    @Operation(
            summary = "Delete cart",
            description = "API to delete cart"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO CONTENT"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{cartId}")
    ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(
            summary = "Update quantity of an item",
            description = "API to update quantity of an item"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{cartId}/items/{productId}/{quantity}")
    ResponseEntity<CartDto> updateQuantity(@PathVariable Long cartId, @PathVariable Long productId, @Max(value = 100, message = "quantity must be less than or equal to 100") @PathVariable int quantity) {
        var cartDto = cartService.updateItemQuantity(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }

}
