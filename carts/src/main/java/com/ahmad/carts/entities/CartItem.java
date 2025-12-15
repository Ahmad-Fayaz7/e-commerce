package com.ahmad.carts.entities;

import com.ahmad.carts.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private BigDecimal price;

}
