package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.ProductsAndCategoriesDTO;
import com.example.aiproductcatalog.web.api.mapper.ProductsAndCategoriesMapper;
import com.example.aiproductcatalog.web.model.ProductsAndCategories;
import com.example.aiproductcatalog.web.repository.ProductsAndCategoriesRepository;
import com.example.aiproductcatalog.web.repository.ProductsAndCategoriesSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsAndCategoriesService {

    private final ProductsAndCategoriesRepository repository;
    private final ProductsAndCategoriesMapper mapper;

    public List<ProductsAndCategoriesDTO> getProductsAndCategories(
            String search,
            List<String> categories,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productName"));

        Specification<ProductsAndCategories> spec =
                ProductsAndCategoriesSpecification.withFilters(search, categories);

        return repository.findAll(spec, pageable)
                .getContent()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}

