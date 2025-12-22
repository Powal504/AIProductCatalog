package com.example.aiproductcatalog.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Integer id;
    private String name;
    private String image;
    private Integer categoryId;
    private String categoryName;
}