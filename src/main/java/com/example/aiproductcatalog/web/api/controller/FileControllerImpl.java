package com.example.aiproductcatalog.web.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileControllerImpl implements FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.products-dir}")
    private String productsDir;


    @Override
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only image files are allowed");
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("File size exceeds 5MB");
            }

            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = (originalFilename != null && originalFilename.contains("."))
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".jpg";

            String baseName = (originalFilename != null && originalFilename.contains("."))
                    ? originalFilename.substring(0, originalFilename.lastIndexOf("."))
                    : originalFilename;

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String fileName = baseName + extension;
            int count = 1;
            Path targetLocation = uploadPath.resolve(fileName);
            while (Files.exists(targetLocation)) {
                fileName = baseName + count + extension;
                targetLocation = uploadPath.resolve(fileName);
                count++;
            }

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/api/files/" + fileName;
            return ResponseEntity.ok(fileUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    @Override
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable("fileName") String fileName) {
        try {

            Path productsPath = Paths.get(productsDir).toAbsolutePath().normalize();
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            List<Path> searchDirs = List.of(productsPath, uploadPath);

            Resource resource = null;
            Path foundFilePath = null;

            for (Path dir : searchDirs) {
                Path candidate = dir.resolve(fileName).normalize();

                if (candidate.startsWith(dir) && Files.exists(candidate) && Files.isReadable(candidate)) {
                    resource = new UrlResource(candidate.toUri());
                    foundFilePath = candidate;
                    break;
                }
            }

            if (resource == null || !resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            Path finalFoundFilePath = foundFilePath;
            MediaType mediaType = MediaTypeFactory.getMediaType(fileName)
                    .orElseGet(() -> {
                        try {
                            String ct = Files.probeContentType(finalFoundFilePath);
                            return (ct != null
                                    ? MediaType.parseMediaType(ct)
                                    : MediaType.APPLICATION_OCTET_STREAM);
                        } catch (IOException ex) {
                            return MediaType.APPLICATION_OCTET_STREAM;
                        }
                    });

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(mediaType)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteImage(@PathVariable("fileName") String fileName) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).normalize();

            if (!filePath.startsWith(uploadPath)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            boolean deleted = Files.deleteIfExists(filePath);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    @GetMapping
    public ResponseEntity<List<String>> listProductFiles() {
        try {
            Path uploadPath = Paths.get(productsDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<String> files = Files.list(uploadPath)
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .toList();

            return ResponseEntity.ok(files);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}