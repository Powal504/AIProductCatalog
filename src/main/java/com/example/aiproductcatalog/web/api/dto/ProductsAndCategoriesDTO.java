package com.example.aiproductcatalog.web.api.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsAndCategoriesDTO {

    private Integer id;

    private String productName;

    private String categoryName;

    private String imageName;
}
