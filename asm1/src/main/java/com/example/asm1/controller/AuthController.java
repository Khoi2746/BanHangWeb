package com.example.asm1.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.asm1.Entity.RegisterForm;

@Controller
public class AuthController {

    // 1. Hiển thị trang đăng ký (GET)
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        // Tạo một object rỗng để hứng dữ liệu
        model.addAttribute("registerForm", new RegisterForm());
        return "register"; // Trả về file register.html
    }

    // 2. Xử lý khi bấm nút "Create Account" (POST)
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                                  BindingResult bindingResult,
                                  Model model) {

        // Nếu có lỗi (để trống, pass ngắn...) -> Trả về lại trang đăng ký kèm lỗi
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // --- XỬ LÝ LOGIC LƯU DB Ở ĐÂY ---
        // Ví dụ: userService.save(registerForm);
        System.out.println("Đăng ký thành công cho: " + registerForm.getFirstName());

        // Đăng ký xong thì đá về trang home hoặc login
        return "redirect:/home";
    }
}