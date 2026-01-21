package com.ahmad.carts.mapper;

import com.ahmad.carts.dtos.CartDto;
import com.ahmad.carts.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICartMapper {
    CartDto toDto(Cart cart);
    Cart toEntity(CartDto cartDto);
}
