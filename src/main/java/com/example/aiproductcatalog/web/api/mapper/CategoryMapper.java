package com.example.aiproductcatalog.web.api.mapper;

import com.example.aiproductcatalog.web.api.dto.CategoryDTO;
import com.example.aiproductcatalog.web.model.Category;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDto(Category category);
}