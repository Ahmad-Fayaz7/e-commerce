package com.ahmad.products.controllers;

import com.ahmad.products.constants.ProductConstants;
import com.ahmad.products.dtos.ErrorResponseDto;
import com.ahmad.products.dtos.ProductDto;
import com.ahmad.products.dtos.ResponseDto;
import com.ahmad.products.entities.Product;
import com.ahmad.products.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@AllArgsConstructor
@Tag(
        name = "CRUD Product REST API for e-commerce",
        description = "CREAT, READ, UPDATE, DELETE API for products"
)
@RequestMapping(path = "/api/products", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductController {
    private final IProductService productService;
    @Operation(
            summary = "Create product",
            description = "This endpoint is used to creat a product"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Created"
    )
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDto productDto) {
        var created = productService.createProduct(productDto);
        return ResponseEntity.ok(created);
    }
    @Operation(
            summary = "Get all products",
            description = "This endpoint is used to get all products"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos;
        productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
    @Operation(
            summary = "Get a product",
            description = "This endpoint is used to get a prodcut's details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@Pattern(regexp = "^(-?\\d{1,19})$", message = "Id is not correct.") @PathVariable String id) {
        var product = productService.getProductById(Long.valueOf(id));
        return ResponseEntity.ok(product);
    }

    @Operation(
            summary = "Update product",
            description = "This endpoint is used to update a product"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Http Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = "Expectation failed."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Http Status Internal Error.",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateProduct(@PathVariable String id, @Valid @RequestBody ProductDto productDto) {
        var updatedProduct = productService.updateProduct(Long.valueOf(id), productDto);
        if (updatedProduct != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                    ProductConstants.STATUS_200, ProductConstants.MESSAGE_200
            ));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(
                    ProductConstants.STATUS_417, ProductConstants.MESSAGE_UPDATE_417
            ));
        }
    }

    @Operation(
            summary = "Delete product",
            description = "This endpoint is used to delete a product"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status OK"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteProduct(@Pattern(regexp = "^(-?\\d{1,19})$", message = "Id is not correct.") @PathVariable String id) {
        productService.deleteProduct(Long.valueOf(id));
        var response = new ResponseDto(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
