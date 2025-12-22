package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO addCategory(String name);

    void deleteCategory(Integer id);
}
