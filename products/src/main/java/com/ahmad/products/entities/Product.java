package com.ahmad.products.entities;

import com.ahmad.products.audit.Auditable;
import com.ahmad.products.entities.enums.AvailabilityStatus;
import com.ahmad.products.entities.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Data
@Entity
@Table(name = "products")
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "availability_status")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    @Column(name = "category")
    private String category;
    @Column(name = "brand")
    private String brand;
}
