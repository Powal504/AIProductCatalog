package com.example.aiproductcatalog.web.api.controller;

import com.example.aiproductcatalog.web.api.dto.ProductDTO;
import com.example.aiproductcatalog.web.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@Tag(name = "Products", description = "Endpoints for managing products (ADMIN only)")
public interface ProductController {

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponse(responseCode = "200", description = "List of products returned successfully")
    ResponseEntity<List<ProductDTO>> getAllProducts();

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new product", description = "Creates a new product and assigns it to a category (ADMIN only)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied – only ADMIN users can add products")
    })
    ResponseEntity<ProductDTO> addProduct(
            @Parameter(description = "Product name", required = true) @RequestParam String name,
            @Parameter(description = "Image path in /wwwroot/products", required = true) @RequestParam String image,
            @Parameter(description = "Category ID to assign", required = true) @RequestParam Integer categoryId
    );

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a product", description = "Deletes an existing product (ADMIN only)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied – only ADMIN users can delete products")
    })
    ResponseEntity<Void> deleteProduct(@PathVariable Integer id);
}
