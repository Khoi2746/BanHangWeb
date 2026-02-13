package com.example.asm1.controller;

import jakarta.servlet.http.HttpSession; // Nếu dùng Spring Boot 3

import java.util.ArrayList;
import java.util.List;

// import javax.servlet.http.HttpSession; // Nếu dùng Spring Boot 2 cũ
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    // Đường dẫn: http://localhost:8080/admin/dashboard
    // Hoặc: http://localhost:8080/admin
  @GetMapping({"/dashboard", "/"})
public String showDashboard(Model model, HttpSession session) {
    if (session.getAttribute("loggedInUser") == null) {
        return "redirect:/login";
    }
    
    // --- GIẢ LẬP DỮ LIỆU RECENT ACTIVITY ---
    // Trong thực tế: Em sẽ query từ bảng "ActivityLog" hoặc "Orders"
    List<String> activities = new ArrayList<>();
    activities.add("Admin vừa thêm sản phẩm: Nike Air Force 1");
    activities.add("User 'Hoang' vừa đăng ký tài khoản");
    activities.add("Sản phẩm 'Jordan 4' đang sắp hết hàng (còn 5 đôi)");
    activities.add("Hệ thống hoạt động ổn định");

    model.addAttribute("recentActivities", activities);
    // ----------------------------------------

    return "admin/Main";
}
}