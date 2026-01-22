package com.example.asm1.controller;

import com.example.asm1.Entity.RegisterForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    // 1. GET: Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {

        // --- LẤY EMAIL TỪ SESSION ---
        String receivedEmail = (String) session.getAttribute("userEmail");

        // [CHỐT CHẶN] Nếu chưa có email -> ĐÁ VỀ
        if (receivedEmail == null || receivedEmail.isEmpty()) {

            // --- CÁCH MỚI: LƯU LỖI VÀO SESSION (Chắc chắn nhận được) ---
            session.setAttribute("sessionError", "Please enter your email first!");

            System.out.println("Không có email -> Đá về /checkMail");
            return "redirect:/checkMail";
        }

        // Nếu có email -> Điền vào form
        RegisterForm form = new RegisterForm();
        form.setEmail(receivedEmail);

        model.addAttribute("registerForm", form);
        return "register";
    }

    // 2. POST: Xử lý đăng ký
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                                  BindingResult bindingResult,
                                  HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        System.out.println("Đăng ký thành công: " + registerForm.getEmail());
        session.removeAttribute("userEmail"); // Xóa email đã lưu
        return "redirect:/home";
    }
}