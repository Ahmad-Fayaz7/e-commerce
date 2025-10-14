package com.ahmad.products.repositories;

import com.ahmad.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
