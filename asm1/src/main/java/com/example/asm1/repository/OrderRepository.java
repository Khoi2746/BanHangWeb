// File: OrderRepository.java
package com.example.asm1.repository;
import com.example.asm1.Entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findTop5ByOrderByOrderDateDesc();
    // Sau này có thể thêm hàm: tìm đơn hàng theo User
    // List<Order> findByUserId(Long userId);
}