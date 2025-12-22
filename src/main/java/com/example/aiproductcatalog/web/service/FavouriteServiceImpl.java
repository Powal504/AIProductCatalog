package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;
import com.example.aiproductcatalog.web.api.mapper.ProductMapper;
import com.example.aiproductcatalog.web.model.Category;
import com.example.aiproductcatalog.web.model.Favourite;
import com.example.aiproductcatalog.web.model.ProductInCategory;
import com.example.aiproductcatalog.web.repository.CategoryRepository;
import com.example.aiproductcatalog.web.repository.FavouriteRepository;
import com.example.aiproductcatalog.web.repository.ProductInCategoryRepository;
import com.example.aiproductcatalog.web.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final ProductRepository productRepository;
    private final ProductInCategoryRepository productInCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductDTO> getUserFavourites(Integer userId) {

        return favouriteRepository.findByUserId(userId).stream()
                .map(fav -> productRepository.findById(fav.getProductId()).orElse(null))
                .filter(Objects::nonNull)
                .map(product -> {

                    Optional<ProductInCategory> relation = productInCategoryRepository
                            .findAll()
                            .stream()
                            .filter(link -> link.getProductId().equals(product.getId()))
                            .findFirst();

                    Category category = relation
                            .flatMap(link -> categoryRepository.findById(link.getCategoryId()))
                            .orElse(null);

                    return productMapper.toDto(product, category);
                })
                .toList();
    }

    public void addToFavourites(Integer userId, Integer productId) {
        if (!favouriteRepository.existsByUserIdAndProductId(userId, productId)) {
            favouriteRepository.save(
                    Favourite.builder()
                            .userId(userId)
                            .productId(productId)
                            .build()
            );
        }
    }

    public void removeFromFavourites(Integer userId, Integer productId) {
        favouriteRepository.deleteByUserIdAndProductId(userId, productId);
    }
}

