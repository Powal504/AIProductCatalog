package com.example.aiproductcatalog.web.repository;

import com.example.aiproductcatalog.security.model.User;
import com.example.aiproductcatalog.web.model.Product;
import com.example.aiproductcatalog.web.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByUser(User user);

    Page<Review> findAllByProduct(Product product, Pageable pageable);
}