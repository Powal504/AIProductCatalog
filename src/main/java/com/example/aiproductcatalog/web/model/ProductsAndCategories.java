package com.example.aiproductcatalog.web.model;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products_and_categories")
@Immutable
public class ProductsAndCategories {

    @Id
    @Column(name = "product_name")
    private String productName;

    @Column(name = "category_name")
    private String categoryName;

}
