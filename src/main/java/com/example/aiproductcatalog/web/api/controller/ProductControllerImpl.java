package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;
import com.example.aiproductcatalog.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService service;

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<ProductDTO> addProduct(
            String name, String image, Integer categoryId
    ) {
        return ResponseEntity.status(201).body(service.addProduct(name, image, categoryId));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
