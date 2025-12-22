package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();

    ProductDTO addProduct(String name, String image, Integer categoryId);

    void deleteProduct(Integer id);
}
