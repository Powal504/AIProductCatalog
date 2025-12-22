package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.CategoryDTO;
import com.example.aiproductcatalog.web.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService service;

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<CategoryDTO> addCategory(String name) {
        return ResponseEntity.status(201).body(service.addCategory(name));
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Integer id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

