package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;

import java.util.List;

public interface FavouriteService {

    List<ProductDTO> getUserFavourites(Integer userId);

    void addToFavourites(Integer userId, Integer productId);

    void removeFromFavourites(Integer userId, Integer productId);
}
