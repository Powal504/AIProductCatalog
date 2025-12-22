package com.example.aiproductcatalog.web.repository;

import com.example.aiproductcatalog.web.model.ProductsAndCategories;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductsAndCategoriesSpecification {

    public static Specification<ProductsAndCategories> withFilters(
            String search,
            List<String> categories
    ) {
        return (Root<ProductsAndCategories> root,
                CriteriaQuery<?> query,
                CriteriaBuilder cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // 🔍 SEARCH (product name OR category name)
            if (search != null && !search.isBlank()) {
                String like = "%" + search.toLowerCase() + "%";

                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("productName")), like),
                                cb.like(cb.lower(root.get("categoryName")), like)
                        )
                );
            }
            if (categories != null && !categories.isEmpty()) {
                predicates.add(
                        root.get("categoryName").in(categories)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}