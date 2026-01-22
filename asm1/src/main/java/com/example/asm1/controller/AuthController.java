package com.example.asm1.controller;

import com.example.asm1.Entity.RegisterForm;
import com.example.asm1.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 1. GET: Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        String receivedEmail = (String) session.getAttribute("userEmail");

        // Chặn nếu người dùng chưa qua bước nhập email
        if (receivedEmail == null || receivedEmail.isEmpty()) {
            session.setAttribute("sessionError", "Vui lòng nhập email trước khi đăng ký!");
            return "redirect:/checkMail";
        }

        RegisterForm form = new RegisterForm();
        form.setEmail(receivedEmail);
        model.addAttribute("registerForm", form);
        return "register";
    }

    // 2. POST: Xử lý đăng ký (Lưu vào SQL Server và Redirect sang Login)
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                                  BindingResult bindingResult,
                                  HttpSession session,
                                  Model model) {

        // --- BƯỚC 1: KIỂM TRA LỖI VALIDATION ---
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // --- BƯỚC 2: KIỂM TRA MÃ OTP ---
        String correctOtp = (String) session.getAttribute("otpCode");
        String userEnteredCode = registerForm.getCode();

        if (correctOtp == null || !correctOtp.equals(userEnteredCode)) {
            bindingResult.rejectValue("code", "error.registerForm", "Mã xác nhận không đúng hoặc đã hết hạn!");
            return "register";
        }

        // --- BƯỚC 3: LƯU DATABASE ---
        try {
            // Lưu người dùng vào SQL Server vĩnh viễn
            userRepository.save(registerForm); 
            
            // Xóa dữ liệu tạm trong session cho sạch sẽ
            session.removeAttribute("otpCode");
            session.removeAttribute("userEmail");

            System.out.println("LOG: Đăng ký thành công cho Member: " + registerForm.getEmail());
            
            // Tự động chuyển sang trang Login sau khi thành công
            return "redirect:/login"; 

        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: Email này đã tồn tại trong hệ thống!");
            return "register";
        }
    }

    // 3. GET: Hiển thị trang Login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // 4. POST: Xử lý Đăng nhập (Kiểm tra từ Database)
    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {

        // Tìm kiếm người dùng trong Database theo Email
        RegisterForm userInDb = userRepository.findByEmail(email);

        // So khớp mật khẩu
        if (userInDb != null && userInDb.getPassword().equals(password)) {
            // Lưu thông tin vào session để Header hiển thị tên "Hi, "
            session.setAttribute("loggedInUser", userInDb);
            System.out.println("LOG: Đăng nhập thành công: " + email);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Email hoặc mật khẩu không chính xác!");
            return "login";
        }
    }

    // 5. GET: Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa toàn bộ session
        return "redirect:/home";
    }
}