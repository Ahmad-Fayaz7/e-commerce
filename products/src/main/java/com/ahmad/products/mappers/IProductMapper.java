package com.ahmad.products.mappers;

import com.ahmad.products.dtos.ProductDto;
import com.ahmad.products.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    List<ProductDto> toDtoList(List<Product> products);
    List<Product> toEntityList(List<ProductDto> productDtos);
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);

}
