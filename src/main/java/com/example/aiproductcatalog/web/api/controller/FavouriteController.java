package com.example.aiproductcatalog.web.api.controller;


import com.example.aiproductcatalog.web.api.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/favourites")
@Tag(name = "Favourites", description = "Endpoints for managing user favourite products")
public interface FavouriteController {

    @GetMapping("/{userId}")
    @Operation(summary = "Get user favourites", description = "Returns list of favourite products for a user")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    ResponseEntity<List<ProductDTO>> getUserFavourites(@PathVariable Integer userId);

    @PostMapping("/{userId}/{productId}")
    @Operation(summary = "Add product to favourites", description = "Adds a product to user's favourite list")
    @ApiResponse(responseCode = "201", description = "Added successfully")
    ResponseEntity<Void> addToFavourites(@PathVariable Integer userId, @PathVariable Integer productId);

    @DeleteMapping("/{userId}/{productId}")
    @Operation(summary = "Remove product from favourites", description = "Removes a product from user's favourite list")
    @ApiResponse(responseCode = "204", description = "Removed successfully")
    ResponseEntity<Void> removeFromFavourites(@PathVariable Integer userId, @PathVariable Integer productId);
}
