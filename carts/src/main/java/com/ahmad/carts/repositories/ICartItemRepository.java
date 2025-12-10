package com.ahmad.carts.repositories;

import com.ahmad.carts.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
}
