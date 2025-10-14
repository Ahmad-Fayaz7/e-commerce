package com.ahmad.products.services.impl;

import com.ahmad.products.dtos.ProductDto;
import com.ahmad.products.entities.Product;
import com.ahmad.products.exception.ProductAlreadyExistsException;
import com.ahmad.products.exception.ProductException;
import com.ahmad.products.exception.ProductNotFoundException;
import com.ahmad.products.exception.ResourceNotFoundException;
import com.ahmad.products.mappers.IProductMapper;
import com.ahmad.products.repositories.IProductRepository;
import com.ahmad.products.services.IProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {
    private IProductRepository productRepository;
    private IProductMapper productMapper;
    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    public ProductDto getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", String.valueOf(id)));
        return productMapper.toDto(product);
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        var result = productRepository.findByName(productDto.getName());
        if (result.isPresent())
            throw new ProductAlreadyExistsException("Product already exists.");
        return productRepository.save(productMapper.toEntity(productDto));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDetails) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", String.valueOf(id)));
        productMapper.updateEntityFromDto(productDetails, product);
        var saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }
}
