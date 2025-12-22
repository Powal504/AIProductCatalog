package com.example.aiproductcatalog.web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products_in_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_products")
    private Integer productId;

    @Column(name = "id_categories")
    private Integer categoryId;
}
