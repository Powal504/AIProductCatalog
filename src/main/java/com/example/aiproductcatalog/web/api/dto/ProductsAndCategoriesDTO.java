package com.example.aiproductcatalog.web.api.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsAndCategoriesDTO {

    private String productName;

    private String categoryName;
}
