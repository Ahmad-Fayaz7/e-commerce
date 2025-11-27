package com.ahmad.carts.entities;

import com.ahmad.carts.entities.enums.Currency;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "total_items")
    private int totalItems;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @OneToMany(mappedBy = "cartId")
    private List<CartItem> items;
}