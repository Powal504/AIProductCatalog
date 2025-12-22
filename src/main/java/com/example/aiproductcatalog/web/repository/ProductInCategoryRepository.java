package com.example.aiproductcatalog.web.repository;

import com.example.aiproductcatalog.web.model.ProductInCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInCategoryRepository extends JpaRepository<ProductInCategory, Integer> {
}