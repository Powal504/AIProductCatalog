package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories (ADMIN only)")
public interface CategoryController {

    @GetMapping
    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    ResponseEntity<List<CategoryDTO>> getAllCategories();

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new category", description = "Creates a new category (ADMIN only)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied – only ADMIN users can create categories")
    })
    ResponseEntity<CategoryDTO> addCategory(@Parameter(description = "Category name", required = true) @RequestParam String name);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category", description = "Deletes an existing category (ADMIN only)")
    ResponseEntity<Void> deleteCategory(@PathVariable Integer id);
}
