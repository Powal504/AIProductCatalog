package com.example.aiproductcatalog.web.model;

import com.google.errorprone.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "products_and_categories")
@Immutable
@Entity
public class ProductsAndCategories {

    @Id
    private String id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "image_name")
    private String imageName;

}
