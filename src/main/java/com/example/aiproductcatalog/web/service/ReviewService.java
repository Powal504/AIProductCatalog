package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.web.api.dto.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getUserReviews(String userEmail);
    ReviewDTO addReview(String userEmail, Integer productId, String text, Float rating);
    void deleteReview(String userEmail, Integer reviewId);
    Page<ReviewDTO> getReviewsForProduct(Integer productId, int page, int size);
    ReviewDTO updateReview(String userEmail, Integer reviewId, String newText, Float newRating);
    Float predictRating(String text);
}
