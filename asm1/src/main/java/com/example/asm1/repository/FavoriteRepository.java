package com.example.asm1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.asm1.Entity.Favorite;
import com.example.asm1.Entity.RegisterForm;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    // Tìm danh sách yêu thích của một User cụ thể
    List<Favorite> findByUser(RegisterForm user);

    Favorite findByUserAndProductId(RegisterForm loggedInUser, Long productId);
}