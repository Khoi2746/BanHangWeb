package com.example.asm1.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ PHẢI LÀ USER ENTITY
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ✅ PRODUCT ENTITY
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
