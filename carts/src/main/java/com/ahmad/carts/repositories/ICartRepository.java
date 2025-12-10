package com.ahmad.carts.repositories;

import com.ahmad.carts.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Long> {
}
