package com.example.aiproductcatalog.web.service;

import com.example.aiproductcatalog.security.model.User;
import com.example.aiproductcatalog.security.repository.UserRepository;
import com.example.aiproductcatalog.web.api.dto.ReviewDTO;
import com.example.aiproductcatalog.web.api.mapper.ReviewMapper;
import com.example.aiproductcatalog.security.exception.PredictionException;
import com.example.aiproductcatalog.web.model.Product;
import com.example.aiproductcatalog.web.model.Review;
import com.example.aiproductcatalog.web.repository.ProductRepository;
import com.example.aiproductcatalog.web.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper mapper;

    @Override
    public List<ReviewDTO> getUserReviews(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return reviewRepository.findAllByUser(user)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ReviewDTO addReview(String userEmail, Integer productId, String text, Float rating) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        if (rating == null) {
            rating = predictRating(text);
        }

        Review review = Review.builder()
                .user(user)
                .product(product)
                .text(text)
                .rating(rating)
                .createdAt(LocalDate.now())
                .build();

        return mapper.toDto(reviewRepository.save(review));
    }

    @Override
    public Float predictRating(String text) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://myFastAPIAppPL.azurewebsites.net/predict";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = Map.of("text", text);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("rating")) {
                throw new PredictionException("Invalid response from AI API", null);
            }

            int rating = (int) body.get("rating");
            rating = Math.min(rating + 1, 5);

            return (float) rating;
        } catch (Exception e) {
            throw new PredictionException("AI rating prediction failed: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteReview(String userEmail, Integer reviewId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        if (!review.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Nie możesz usunąć cudzej recenzji!");
        }

        reviewRepository.delete(review);
    }

    @Override
    public Page<ReviewDTO> getReviewsForProduct(Integer productId, int page, int size) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return reviewRepository.findAllByProduct(product, pageRequest)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public ReviewDTO updateReview(String userEmail, Integer reviewId, String newText, Float newRating) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new RuntimeException("User not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Nie możesz edytować cudzej recenzji!");
        }

        if (newText != null && !newText.isBlank()) {
            review.setText(newText);
        }

        if (newRating != null) {
            review.setRating(newRating);
        } else if (newText != null && !newText.isBlank()) {
            review.setRating(predictRating(newText));
        }
        return mapper.toDto(reviewRepository.save(review));
    }

}
