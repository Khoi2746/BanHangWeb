package com.example.asm1.controller;

import com.example.asm1.Entity.RegisterForm;
import com.example.asm1.Entity.User;
import com.example.asm1.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ========== REGISTER ==========

    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {

        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            session.setAttribute("sessionError", "Vui lòng nhập email trước!");
            return "redirect:/checkMail";
        }

        RegisterForm form = new RegisterForm();
        form.setEmail(email);
        model.addAttribute("registerForm", form);
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(
            @Valid @ModelAttribute("registerForm") RegisterForm registerForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        String otp = (String) session.getAttribute("otpCode");
        if (otp == null || !otp.equals(registerForm.getCode())) {
            bindingResult.rejectValue("code", "error.registerForm", "Mã xác nhận không đúng!");
            return "register";
        }

        try {
            User user = new User();
            user.setEmail(registerForm.getEmail());
            user.setPassword(registerForm.getPassword()); // TODO: hash
            user.setFirstName(registerForm.getFirstName());
            user.setSurname(registerForm.getSurname());
            user.setShoppingPreference(registerForm.getShoppingPreference());
            user.setDobDay(registerForm.getDobDay());
            user.setDobMonth(registerForm.getDobMonth());
            user.setDobYear(registerForm.getDobYear());
            user.setEmailSignup(registerForm.isEmailSignup());
            user.setAgreeTerms(registerForm.isAgreeTerms());
            user.setRoleId(2); // USER

            userRepository.save(user);

            session.removeAttribute("otpCode");
            session.removeAttribute("userEmail");

            return "redirect:/login";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đăng ký thất bại!");
            return "register";
        }
    }

    // ========== LOGIN ==========

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/home";
        }

        model.addAttribute("error", "Email hoặc mật khẩu không chính xác!");
        return "login";
    }

    // ========== LOGOUT ==========

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}
