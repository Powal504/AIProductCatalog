package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.ReviewDTO;
import com.example.aiproductcatalog.web.repository.ProductRepository;
import com.example.aiproductcatalog.web.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {

    private final ReviewService reviewService;

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<List<ReviewDTO>> getMyReviews(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reviewService.getUserReviews(email));
    }

    @Override
    public ResponseEntity<ReviewDTO> addReview(Integer productId, String text, Float rating, Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.status(201).body(reviewService.addReview(email, productId, text, rating));
    }

    @Override
    public ResponseEntity<Void> deleteReview(Integer id, Authentication authentication) {
        String email = authentication.getName();
        reviewService.deleteReview(email, id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> getReviewsForProduct(Integer productId, int page, int size) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        var reviews = reviewService.getReviewsForProduct(productId, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("reviews", reviews);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ReviewDTO> updateReview(Integer id, String text, Float rating, Authentication authentication) {
        String email = authentication.getName();
        ReviewDTO updatedReview = reviewService.updateReview(email, id, text, rating);
        return ResponseEntity.ok(updatedReview);
    }

    @Override
    public ResponseEntity<Map<String, Float>> predictRating(String text) {
        try {
            Float rating = reviewService.predictRating(text);
            return ResponseEntity.ok(Map.of("predictedRating", rating));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", 0f));
        }
    }



}

