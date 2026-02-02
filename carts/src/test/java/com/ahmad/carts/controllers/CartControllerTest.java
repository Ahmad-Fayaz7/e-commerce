package com.ahmad.carts.controllers;

import com.ahmad.carts.dtos.CartDto;
import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.services.ICartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@ExtendWith(SpringExtension.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private CartDto cartDto;
    private CartItemDto cartItemDto;
    @MockitoBean
    private ICartService cartService;


    @BeforeEach
    void setup() {
        cartDto = createCartDto();
        cartItemDto = createCartItemDto();
    }


    @Test
    void addItemToCart_returnsOkAndCartDto() throws Exception {
        Long USER_ID = 1L;
        when(cartService.addItemToCart(eq(USER_ID), any(CartItemDto.class))).thenReturn(cartDto);
        var mvcResult =
                mockMvc.perform(post("/api/carts/{customerId}/items", USER_ID).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cartItemDto))).andExpect(status().isOk()).andReturn();
        var result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CartDto.class);
        assertEquals(cartDto.getCartId(), result.getCartId());
    }

    @Test
    void addItemToCart_invalidRequest_returnsBadRequest() throws Exception {
        cartItemDto.setQuantity(-5);
        mockMvc.perform(post("/api/carts/{customerId}/items", "1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cartItemDto))).andExpect(status().isBadRequest());
        verify(cartService, never()).addItemToCart(any(), any());
    }

    @Test
    void getCart_returnsOkAndCartDto() throws Exception {
        when(cartService.getCart(cartDto.getCartId())).thenReturn(cartDto);
        var mvcResult =
                mockMvc.perform(get("/api/carts/{cartId}", cartDto.getCartId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        var result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CartDto.class);
        verify(cartService).getCart(cartDto.getCartId());
        assertEquals(result.getCartId(), cartDto.getCartId());
    }

    @Test
    void removeItemFromCart_returnsOkAndCartDto() throws Exception {
        when(cartService.removeItemFromCart(cartDto.getCartId(), cartItemDto.getProductId())).thenReturn(cartDto);
        var mvcResult = mockMvc.perform(delete("/api/carts/{cartId}/items/{productId}", cartDto.getCartId(),
                cartItemDto.getProductId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        var result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CartDto.class);
        verify(cartService).removeItemFromCart(cartDto.getCartId(), cartItemDto.getProductId());
        assertEquals(result.getCartId(), cartDto.getCartId());
    }

    @Test
    void deleteCart_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/carts/{cartId}", cartDto.getCartId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent()).andReturn();
        verify(cartService).deleteCart(cartDto.getCartId());
    }

    @Test
    void updateQuantity_returnsOkAndCartDto() throws Exception {
        cartItemDto.setQuantity(5);
        cartDto.getItems().add(cartItemDto);
        when(cartService.updateItemQuantity(cartDto.getCartId(), cartItemDto.getProductId(), 5)).thenReturn(cartDto);
        MvcResult mvcResult = mockMvc.perform(put("/api/carts/{cartId}/items/{productId}/{quantity}",
                cartDto.getCartId(), cartItemDto.getProductId(), 5).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        CartDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CartDto.class);
        verify(cartService).updateItemQuantity(cartDto.getCartId(), cartItemDto.getProductId(), 5);
        assertEquals(5, result.getItems().get(0).getQuantity());
    }


    private CartItemDto createCartItemDto() {
        return CartItemDto.builder().quantity(2).price(BigDecimal.valueOf(10)).productId(3L).build();
    }

    private CartDto createCartDto() {
        CartDto cartDto = new CartDto();
        cartDto.setCartId(3L);
        cartDto.setTotalItems(2);
        cartDto.setTotalPrice(BigDecimal.valueOf(20));
        cartDto.setCurrency(Currency.USD);
        return cartDto;
    }
}