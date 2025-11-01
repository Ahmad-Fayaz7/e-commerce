package com.ahmad.products.services;

import com.ahmad.products.dtos.ProductDto;
import com.ahmad.products.entities.Product;

import java.util.List;
public interface IProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    Product createProduct(ProductDto productDto);
    void deleteProduct(Long id);
    ProductDto updateProduct(Long id, ProductDto productDetails);
}
