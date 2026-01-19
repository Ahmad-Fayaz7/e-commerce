package com.ahmad.carts.mapper;

import com.ahmad.carts.dtos.CartItemDto;
import com.ahmad.carts.entities.CartItem;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ICartItemMapper {
    CartItemDto toDto(CartItem cartItem);
    CartItem toEntity(CartItemDto cartItemDto);
    List<CartItemDto> toDtoList(List<CartItem> cartItems);
    List<CartItem> toEntityList(List<CartItemDto> cartItemDtoList);
}
