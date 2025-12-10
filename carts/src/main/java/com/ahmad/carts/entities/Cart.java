package com.ahmad.carts.entities;

import com.ahmad.carts.audit.Auditable;
import com.ahmad.carts.entities.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart extends Auditable {
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
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CartItem> items;

    public void addToCart(CartItem item) {
        items.add(item);
        item.setCart(this);
    }


}