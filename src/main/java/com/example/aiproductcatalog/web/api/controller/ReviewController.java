package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.ReviewDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/reviews")
@Tag(name = "Reviews", description = "Endpoints for managing user reviews")
public interface ReviewController {

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get my reviews", description = "Returns all reviews created by the current user")
    ResponseEntity<List<ReviewDTO>> getMyReviews(Authentication authentication);

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Add review", description = "Adds a new review for a product")
    ResponseEntity<ReviewDTO> addReview(
            @RequestParam Integer productId,
            @RequestParam String text,
            @RequestParam(required = false) Float rating,
            Authentication authentication
    );

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Delete review", description = "Deletes the user's own review")
    ResponseEntity<Void> deleteReview(@PathVariable Integer id, Authentication authentication);

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get reviews for a product", description = "Returns all reviews for a given product with pagination")
    ResponseEntity<?> getReviewsForProduct(
            @PathVariable Integer productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Update review", description = "Updates the user's own review")
    ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Integer id,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Float rating,
            Authentication authentication
    );

    @PostMapping("/predict")
    @Operation(summary = "Predict rating", description = "Get AI suggested rating for review text")
    ResponseEntity<Map<String, Float>> predictRating(@RequestParam String text);


}
