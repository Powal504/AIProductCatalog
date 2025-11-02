package com.example.aiproductcatalog.web.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/files")
@Tag(name = "Files", description = "Endpoint for retrieving images")
public interface FileController {

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @Operation(summary = "Upload image", description = "Uploads an image file to the server")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<String> uploadImage(
            @Parameter(
                    description = "Image file",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestParam("file") MultipartFile file
    );

    @GetMapping("/{fileName}")
    @Operation(
            summary = "Get image",
            description = "Retrieves an image for frontend display"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Image retrieved successfully",
                    content = @Content(mediaType = "image/*", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    ResponseEntity<Resource> getImage(@PathVariable String fileName);

    @DeleteMapping("/{fileName:.+}")
    @Operation(summary = "Delete image", description = "Deletes a previously uploaded image")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Image deleted"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    ResponseEntity<Void> deleteImage(@PathVariable("fileName") String fileName);
}

