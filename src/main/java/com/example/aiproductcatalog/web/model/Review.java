package com.example.aiproductcatalog.web.model;

import com.example.aiproductcatalog.security.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    private String text;

    @Column(name = "raiting")
    private Float rating;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
