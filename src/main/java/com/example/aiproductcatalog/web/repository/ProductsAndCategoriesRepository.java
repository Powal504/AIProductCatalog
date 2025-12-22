package com.example.aiproductcatalog.web.repository;

import com.example.aiproductcatalog.web.model.ProductsAndCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductsAndCategoriesRepository
        extends JpaRepository<ProductsAndCategories, String>,
        JpaSpecificationExecutor<ProductsAndCategories> {
}
