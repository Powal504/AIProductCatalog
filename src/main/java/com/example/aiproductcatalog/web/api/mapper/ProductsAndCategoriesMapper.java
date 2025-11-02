package com.example.aiproductcatalog.web.api.mapper;

import com.example.aiproductcatalog.web.api.dto.ProductsAndCategoriesDTO;
import com.example.aiproductcatalog.web.model.ProductsAndCategories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsAndCategoriesMapper {
    ProductsAndCategoriesDTO toDTO(ProductsAndCategories entity);
}