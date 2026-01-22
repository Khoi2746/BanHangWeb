package com.example.asm1.repository;

import com.example.asm1.Entity.RegisterForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<RegisterForm, Long> {
    // Hàm này giúp tìm User trong DB dựa trên Email (dùng cho chức năng Login sau này)
    RegisterForm findByEmail(String email);
}