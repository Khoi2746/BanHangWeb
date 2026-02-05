package com.example.asm1.repository;

import com.example.asm1.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Sản phẩm nổi bật
    List<Product> findByIsFeaturedTrue();

    // Tìm theo tên
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // Filter category + gender
    @Query("""
    SELECT p FROM Product p
    WHERE (:category IS NULL OR p.category = :category)
    AND (:gender IS NULL OR p.gender = :gender)
""")
List<Product> filterProducts(
        @Param("category") String category,
        @Param("gender") String gender
);

    
}
