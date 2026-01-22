package com.example.asm1.controller;

import com.example.asm1.Entity.MailForm;
import jakarta.servlet.http.HttpSession; // <--- Import
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckMailController {

    // 1. GET: Hiển thị trang (ĐÂY LÀ CHỖ CẦN SỬA)
    @GetMapping("/checkMail")
    public String showLoginPage(Model model, HttpSession session) { // <--- Thêm HttpSession
        
        // Tạo form mới
        model.addAttribute("ChangeMail", new MailForm());

        // --- [QUAN TRỌNG] LẤY LỖI TỪ SESSION RA ---
        // AuthController đã nhét lỗi vào cái hộp tên là "sessionError"
        String error = (String) session.getAttribute("sessionError");
        
        if (error != null) {
            System.out.println("LOG: Tìm thấy lỗi từ Register đá về: " + error);
            
            // Chuyền lỗi này sang Model (đặt tên là "errorMessage" để HTML hứng)
            model.addAttribute("errorMessage", error);
            
            // Xóa ngay để F5 không hiện lại
            session.removeAttribute("sessionError");
        }

        return "ChangeMail"; 
    }

    // 2. POST: Xử lý (Giữ nguyên code debug của em)
    @PostMapping("/login/check-email")
    public String checkEmail(@Valid @ModelAttribute("ChangeMail") MailForm mailForm,
                             BindingResult bindingResult, 
                             HttpSession session) { 
        
        System.out.println("--- DEBUG START ---");
        System.out.println("Email nhận được từ form: '" + mailForm.getEmail() + "'");

        if (bindingResult.hasErrors()) {
            System.out.println("LỖI 1: Dính Validation!");
            return "ChangeMail"; 
        }

        if (mailForm.getEmail() == null || mailForm.getEmail().trim().isEmpty()) {
            System.out.println("LỖI 2: Email rỗng!");
            return "ChangeMail"; 
        }

        System.out.println("SUCCESS: Chuyển sang Register...");
        session.setAttribute("userEmail", mailForm.getEmail());

        return "redirect:/register"; 
    }
}