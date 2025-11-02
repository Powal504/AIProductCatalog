package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.ProductsAndCategoriesDTO;
import com.example.aiproductcatalog.web.service.ProductsAndCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductsAndCategoriesControllerImpl implements ProductsAndCategoriesController {

    private final ProductsAndCategoriesService service;

    @Override
    public Page<ProductsAndCategoriesDTO> getProductsAndCategories(int page, int size, String search) {
        return service.getProductsAndCategories(search, page, size);
    }
}