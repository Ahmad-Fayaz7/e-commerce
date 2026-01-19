package com.ahmad.carts.entities;

import com.ahmad.carts.audit.Auditable;
import com.ahmad.carts.entities.enums.Currency;
import com.ahmad.carts.exceptions.CartItemNotFoundException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Entity
@Table(name = "carts")
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "total_items")
    private int totalItems;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private final List<CartItem> items = new ArrayList<>();

    @Builder
    public Cart(Long userId, Currency currency, List<CartItem> items) {
        this.userId = userId;
        this.currency = currency;
        if (items != null) this.items.addAll(items);
        calculateTotals();
    }

    public void addItemToCart(CartItem item) {
        items.add(item);
        item.setCart(this);
        calculateTotals();
    }

    public void updateQuantity(Long productId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity cannot be less than 1");
        }
        var item = getItemByProductId(productId);
        if (item.isEmpty())
            throw new CartItemNotFoundException("Item not found");
        item.get().setQuantity(quantity);
        calculateTotals();
    }

    public void removeItemByProductId(Long productId) {
        var item =
                items.stream().filter(i -> i.getProductId().equals(productId)).findFirst().orElseThrow(() -> new CartItemNotFoundException("Item does not exist in cart"));
        items.remove(item);
        calculateTotals();
    }


    public Optional<CartItem> getItemByProductId(Long productId) {
        for (CartItem item : items) {
            if (productId.equals(item.getProductId())) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public void calculateTotals() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalItems = 0;
        for (var item : items) {
            totalPrice = totalPrice
                    .add(item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
            totalItems = totalItems + item.getQuantity();
        }
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
    }

}