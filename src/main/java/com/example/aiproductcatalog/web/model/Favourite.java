package com.example.aiproductcatalog.web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favourites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_user", nullable = false)
    private Integer userId;

    @Column(name = "id_products", nullable = false)
    private Integer productId;
}
