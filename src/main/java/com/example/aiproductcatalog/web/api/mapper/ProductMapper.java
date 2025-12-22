package com.example.aiproductcatalog.web.api.mapper;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;
import com.example.aiproductcatalog.web.model.Category;
import com.example.aiproductcatalog.web.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "product.id", target = "id")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.image", target = "image")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDTO toDto(Product product, Category category);

}
