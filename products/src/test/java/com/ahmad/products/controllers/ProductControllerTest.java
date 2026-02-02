package com.ahmad.products.controllers;

import com.ahmad.products.dtos.ProductDto;
import com.ahmad.products.entities.Product;
import com.ahmad.products.entities.enums.AvailabilityStatus;
import com.ahmad.products.entities.enums.Currency;
import com.ahmad.products.mappers.IProductMapper;
import com.ahmad.products.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    IProductService productService;
    @MockitoBean
    IProductMapper productMapper;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setup() {
        productDto = createTestProductDto();

        product = createTestProduct();
    }

    @Test
    void getProductById() throws Exception {
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);
        when(productService.getProductById(any(Long.class))).thenReturn(productDto);
        MvcResult mvcResult =
                mockMvc.perform(get("/api/products/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDto.class);
        assertEquals("Iphone 17", result.getName());
    }

    @Test
    void createProductById() throws Exception {
        when(productService.createProduct(any(ProductDto.class))).thenReturn(product);
        MvcResult mvcResult = mockMvc.perform(post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productDto)))
                        .andExpect(status().isOk()).andReturn();
        Product result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);
        assertEquals("Iphone 17", result.getName());
        assertEquals("Apple", result.getBrand());
    }


    // Factory method for reusable Product instances
    private Product createTestProduct() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Iphone 17");
        p.setDescription("Some description");
        p.setPrice(BigDecimal.valueOf(1100));
        p.setCurrency(Currency.USD);
        p.setAvailabilityStatus(AvailabilityStatus.IN_STOCK);
        p.setCategory("electronics");
        p.setBrand("Apple");
        return p;
    }


    private ProductDto createTestProductDto() {
        ProductDto p = new ProductDto();
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
