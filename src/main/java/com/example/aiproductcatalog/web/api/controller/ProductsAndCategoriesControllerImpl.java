package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.ProductsAndCategoriesDTO;
import com.example.aiproductcatalog.web.service.ProductsAndCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products-categories")
public class ProductsAndCategoriesControllerImpl {

    private final ProductsAndCategoriesService service;

    @GetMapping
    public List<ProductsAndCategoriesDTO> getProductsAndCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<String> categories
    ) {
        return service.getProductsAndCategories(search, categories, page, size);
    }
}
