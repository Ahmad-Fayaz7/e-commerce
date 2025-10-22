package com.ahmad.products.repositories;


import com.ahmad.products.entities.Product;
import com.ahmad.products.entities.enums.AvailabilityStatus;
import com.ahmad.products.entities.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
/* COMMENT @EnableJpaAuditing(auditorAwareRef = "auditorProvider")
ON APPLICATION TEMPORARILY BEFORE RUNNING TESTS*/
@DataJpaTest
@ActiveProfiles("test") // Automatically loads application-test.yml
class ProductRepositoryTest {

    @Autowired
    private IProductRepository productRepository;
    private Product product;
    @BeforeEach
    void setUp() {
        product = createTestProduct();
    }
    @Test
    void shouldSaveProductSuccessfully() {
        // when
        Product saved = productRepository.save(product);

        // then
        assertNotNull(saved.getId()); // ID should be generated
        assertEquals("Iphone 17", saved.getName());
        assertEquals(Currency.USD, saved.getCurrency());
        assertEquals(AvailabilityStatus.IN_STOCK, saved.getAvailabilityStatus());
    }
    @DisplayName("Repository should find product by its ID")
    @Test
    void shouldFindProductById() {
        // given
        var saved = productRepository.save(product);

        // when
        var found = productRepository.findById(saved.getId());

        // then
        assertTrue(found.isPresent(), "Product should be found by Id");
        Product foundProduct = found.get();
        assertEquals(product.getName(), foundProduct.getName());
        assertEquals(Currency.USD, foundProduct.getCurrency());
        assertEquals(AvailabilityStatus.IN_STOCK, foundProduct.getAvailabilityStatus());
    }

    @DisplayName("Should delete a product by Id")
    @Test
    void shouldDeleteProduct(){
        var saved = productRepository.save(product);
        var found = productRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        var foundProduct = found.get();
        assertEquals("Iphone 17", foundProduct.getName());
        productRepository.deleteById(saved.getId());
        var fetched = productRepository.findById(saved.getId());
        assertFalse(fetched.isPresent());
    }




    // Factory method for reusable Product instances
    private Product createTestProduct() {
        Product p = new Product();
        p.setName("Iphone 17");
        p.setDescription("Some description");
        p.setPrice(BigDecimal.valueOf(1100));
        p.setCurrency(Currency.USD);
        p.setAvailabilityStatus(AvailabilityStatus.IN_STOCK);
        p.setCategory("electronics");
        p.setBrand("Apple");
        return p;
    }
}
