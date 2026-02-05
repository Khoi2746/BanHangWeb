package com.example.asm1.repository;

import com.example.asm1.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Lấy danh sách sản phẩm nổi bật để hiện ở trang chủ
    List<Product> findByIsFeaturedTrue();

    Optional<Product> findById(Long productId); 

    List<Product> findByGenderAndCategory(String gender, String category);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}
