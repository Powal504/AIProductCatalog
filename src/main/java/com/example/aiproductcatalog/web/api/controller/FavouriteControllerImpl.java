package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;
import com.example.aiproductcatalog.web.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavouriteControllerImpl implements FavouriteController {

    private final FavouriteService service;

    @Override
    public ResponseEntity<List<ProductDTO>> getUserFavourites(Integer userId) {
        return ResponseEntity.ok(service.getUserFavourites(userId));
    }

    @Override
    public ResponseEntity<Void> addToFavourites(Integer userId, Integer productId) {
        service.addToFavourites(userId, productId);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> removeFromFavourites(Integer userId, Integer productId) {
        service.removeFromFavourites(userId, productId);
        return ResponseEntity.noContent().build();
    }
}