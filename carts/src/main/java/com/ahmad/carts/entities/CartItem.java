package com.ahmad.carts.entities;

import com.ahmad.carts.entities.enums.Currency;
import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cartId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private BigDecimal price;

}
