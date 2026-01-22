package com.example.asm1.controller;

import com.example.asm1.Entity.Favorite;
import com.example.asm1.Entity.RegisterForm;
import com.example.asm1.Entity.Product;
import com.example.asm1.repository.FavoriteRepository;
import com.example.asm1.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository; // Để tìm sản phẩm trước khi thêm

    // 1. Hiển thị trang Favourites
    @GetMapping("/favorites")
    public String showFavorites(HttpSession session, Model model) {
        // Lấy User từ session (người đang đăng nhập)
        RegisterForm loggedInUser = (RegisterForm) session.getAttribute("loggedInUser");

        // Nếu chưa đăng nhập, đá về trang Login ngay
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Lấy danh sách từ DB
        List<Favorite> favList = favoriteRepository.findByUser(loggedInUser);
        model.addAttribute("favorites", favList);

        return "favorite"; // Trả về file favorites.html của em
    }

    // 2. Thêm hoặc Xóa sản phẩm khỏi danh sách yêu thích
    @PostMapping("/favorites/toggle")
    public String toggleFavorite(@RequestParam("productId") Long productId, HttpSession session) {
        RegisterForm loggedInUser = (RegisterForm) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Kiểm tra xem đã yêu thích chưa
        Favorite existingFav = favoriteRepository.findByUserAndProductId(loggedInUser, productId);

        if (existingFav != null) {
            // Nếu có rồi thì XÓA (unfavorite)
            favoriteRepository.delete(existingFav);
        } else {
            // Nếu chưa có thì THÊM MỚI
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                Favorite newFav = new Favorite();
                newFav.setUser(loggedInUser);
                newFav.setProduct(product);
                favoriteRepository.save(newFav);
            }
        }

        return "redirect:/home"; // Quay lại trang chủ hoặc trang hiện tại
    }
}