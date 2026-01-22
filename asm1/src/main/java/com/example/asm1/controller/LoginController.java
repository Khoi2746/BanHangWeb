package com.example.asm1.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.asm1.Entity.MailForm;

@Controller
public class LoginController {

    // 1. Hiển thị trang nhập Email (GET)
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // Tạo object rỗng để form binding
        model.addAttribute("loginForm", new MailForm());
        return "ChangeMail"; // Trả về file ChangeMail.html
    }

    // 2. Xử lý khi bấm nút Continue (POST)
    @PostMapping("/login/check-email")
    public String checkEmail(@Valid @ModelAttribute("loginForm") MailForm loginForm,
                             BindingResult bindingResult,
                             Model model) {
        
        // Nếu validate lỗi (chưa nhập email, sai định dạng)
        if (bindingResult.hasErrors()) {
            return "ChangeMail"; // Quay lại trang cũ, hiện lỗi đỏ
        }

        // LOGIC GIẢ LẬP:
        // Nếu email tồn tại trong DB -> Chuyển sang trang nhập Password
        // Nếu email chưa có -> Chuyển sang trang Register (như bài trước anh làm)
        
        System.out.println("Email nhận được: " + loginForm.getEmail());

        // Ví dụ: Giả sử email này chưa có, đá sang trang đăng ký
        return "redirect:/register"; 
    }
}