package com.example.asm1.Entity;

import jakarta.persistence.*; // <--- Thêm import này
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity // Đánh dấu đây là một thực thể JPA
@Table(name = "Users") // Tên bảng trong SQL Server
public class RegisterForm {

    @Id // Đánh dấu khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng (Identity 1,1)
    private Long id;

    @Column(nullable = false, unique = true) // Email không được trống và không được trùng
    private String email; 

    @Transient
    @NotBlank(message = "Required")
    private String code;

    @NotBlank(message = "Required")
    @Column(name = "first_name") // Map với cột first_name trong SQL
    private String firstName;

    @NotBlank(message = "Required")
    private String surname;

    @NotBlank(message = "Required")
    @Size(min = 8, message = "Minimum of 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Uppercase, lowercase letters and one number")
    private String password;

    @NotBlank(message = "Please select a preference")
    @Column(name = "shopping_preference")
    private String shoppingPreference;

    @NotBlank(message = "Required")
    @Column(name = "dob_day")
    private String dobDay;

    @NotBlank(message = "Required")
    @Column(name = "dob_month")
    private String dobMonth;

    @NotBlank(message = "Required")
    @Column(name = "dob_year")
    private String dobYear;

    @Column(name = "email_signup")
    private boolean emailSignup; 

    @AssertTrue(message = "You must agree to the terms")
    @Column(name = "agree_terms")
    private boolean agreeTerms;
}