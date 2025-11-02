package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.ProductsAndCategoriesDTO;
import com.example.aiproductcatalog.web.api.mapper.ProductsAndCategoriesMapper;
import com.example.aiproductcatalog.web.model.ProductsAndCategories;
import com.example.aiproductcatalog.web.repository.ProductsAndCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsAndCategoriesService {

    private final ProductsAndCategoriesRepository repository;
    private final ProductsAndCategoriesMapper mapper;

    public Page<ProductsAndCategoriesDTO> getProductsAndCategories(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productName").ascending());

        Page<ProductsAndCategories> results;
        if (search != null && !search.isBlank()) {
            results = repository.findByProductNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(
                    search, search, pageable);
        } else {
            results = repository.findAll(pageable);
        }

        return results.map(mapper::toDTO);
    }
}
