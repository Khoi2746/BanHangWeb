package com.example.asm1.repository;

import com.example.asm1.Entity.Favorite;
import com.example.asm1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    Favorite findByUserAndProduct_Id(User user, Long productId);
}
