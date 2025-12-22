package com.example.aiproductcatalog.web.api.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private Integer id;
    private Integer productId;
    private String productName;
    private String text;
    private Float rating;
    private LocalDate createdAt;
    private String username;
    private String avatarUrl;
}
