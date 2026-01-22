package com.example.asm1.repository;

import com.example.asm1.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Tìm kiếm sản phẩm theo tên (dùng cho thanh Search của Nike)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Tìm kiếm sản phẩm theo danh mục (Men, Women, Kids)
    List<Product> findByCategory(String category);
}