package com.example.asm1.Entity;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data; // Nếu em dùng Lombok, không thì tự generate getter/setter nhé

@Data
public class RegisterForm {

    @NotBlank(message = "Required")
    private String code;

    @NotBlank(message = "Required")
    private String firstName;

    @NotBlank(message = "Required")
    private String surname;

    @NotBlank(message = "Required")
    @Size(min = 8, message = "Minimum of 8 characters")
    private String password;

    @NotBlank(message = "Please select a preference")
    private String shoppingPreference;

    // Ngày sinh tách 3 trường cho giống giao diện
    @NotBlank(message = "Required")
    private String dobDay;

    @NotBlank(message = "Required")
    private String dobMonth;

    @NotBlank(message = "Required")
    private String dobYear;

    private boolean emailSignup; // Checkbox nhận email

    @AssertTrue(message = "You must agree to the terms")
    private boolean agreeTerms; // Checkbox đồng ý điều khoản
}