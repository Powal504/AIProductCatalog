package com.example.aiproductcatalog.web.repository;

import com.example.aiproductcatalog.web.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    List<Favourite> findByUserId(Integer userId);

    boolean existsByUserIdAndProductId(Integer userId, Integer productId);

    void deleteByUserIdAndProductId(Integer userId, Integer productId);
}