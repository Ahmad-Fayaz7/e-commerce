package com.ahmad.products.services;

import com.ahmad.products.dtos.ProductDto;
import com.ahmad.products.entities.Product;
import com.ahmad.products.entities.enums.AvailabilityStatus;
import com.ahmad.products.entities.enums.Currency;
import com.ahmad.products.exception.ProductAlreadyExistsException;
import com.ahmad.products.exception.ResourceNotFoundException;
import com.ahmad.products.mappers.IProductMapper;
import com.ahmad.products.repositories.IProductRepository;
import com.ahmad.products.repositories.impl.ProductRepositoryImpl;
import com.ahmad.products.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private IProductRepository productRepository;
    @Mock
    private IProductMapper productMapper;
    @InjectMocks
    ProductServiceImpl productService;
    private final String PRODUCT_NAME = "Iphone 17";
    Product product = new Product(
            1L,
            PRODUCT_NAME,
            "some text",
            BigDecimal.valueOf(1100),
            Currency.USD,
            AvailabilityStatus.IN_STOCK,
            "electronics",
            "Apple");

    ProductDto productDto = new ProductDto(
            PRODUCT_NAME,
            "some text",
            BigDecimal.valueOf(1100),
            Currency.USD,
            AvailabilityStatus.IN_STOCK,
            "electronics",
            "Apple");

    @Test
    public void shouldReturnProductById() {
        when(productMapper.toDto(product)).thenReturn(productDto);
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        var result = productService.getProductById(1L);
        assertEquals(PRODUCT_NAME, result.getName());
        verify(productMapper).toDto(product);
        verify(productRepository).findById(1L);
    }

    @Test
    public void getProductById_shouldThrowResourceNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
        verify(productMapper, never()).toDto(product);
    }

    @Test
    public void shouldCreateProduct() {
        when(productRepository.findByName(productDto.getName())).thenReturn(Optional.empty());
        when(productMapper.toEntity(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        var result = productService.createProduct(productDto);
        assertNotNull(result);
        assertEquals(PRODUCT_NAME, result.getName());
        verify(productRepository).findByName(PRODUCT_NAME);
        verify(productMapper).toEntity(productDto);
        verify(productRepository).save(product);
    }

    @Test
    public void createProduct_shouldThrowProductExists() {
        when(productRepository.findByName(PRODUCT_NAME)).thenReturn(Optional.of(product));
        var result = assertThrows(ProductAlreadyExistsException.class, () -> productService.createProduct(productDto));
        assertEquals("Product already exists.", result.getMessage());
        verify(productRepository).findByName(PRODUCT_NAME);
        verify(productRepository, never()).save(product);
    }

    @Test
    public void shouldDeleteProductById() {
        productService.deleteProduct(1L);
        verify(productRepository).deleteById(1L);
        verifyNoMoreInteractions(productRepository);
    }


}
