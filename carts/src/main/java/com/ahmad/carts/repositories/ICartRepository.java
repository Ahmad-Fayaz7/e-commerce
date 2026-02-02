package com.ahmad.carts.repositories;

import com.ahmad.carts.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long id);
}
