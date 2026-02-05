package com.example.asm1.controller;

import com.example.asm1.Entity.CartItem;
import com.example.asm1.Entity.Favorite;
import com.example.asm1.Entity.Product;
import com.example.asm1.Entity.User;
import com.example.asm1.repository.CartItemRepository;
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
public class ShoppingController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository;

    // =========================================================
    // 1. QUAN TRỌNG NHẤT: HÀM HIỂN THỊ GIỎ HÀNG & TÍNH TIỀN
    // =========================================================
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        // a. Lấy danh sách sản phẩm trong giỏ của User này
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        
        // b. Tính tổng tiền (Loop qua từng món để nhân giá x số lượng)
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            // Giả sử mỗi sản phẩm đều có giá, nếu null sẽ lỗi nên cần kiểm tra
            if (item.getProduct().getPrice() != null) {
                totalAmount += item.getProduct().getPrice() * item.getQuantity();
            }
        }

        // c. Gửi dữ liệu sang file HTML
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", totalAmount); // Tạm tính
        model.addAttribute("total", totalAmount);    // Tổng cộng

        // d. Trả về tên file HTML (chữ thường cho chuẩn)
        return "cart"; 
    }

    // =========================================================
    // 2. XỬ LÝ THÊM VÀO GIỎ HÀNG
    // =========================================================
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartItemRepository.save(existingItem);
            } else {
                CartItem newItem = new CartItem();
                newItem.setUser(user);
                newItem.setProduct(product);
                newItem.setQuantity(quantity);
                cartItemRepository.save(newItem);
            }
        }

        // QUAN TRỌNG: Dùng "redirect" để chuyển sang hàm viewCart ở trên
        // Nó sẽ giúp tính toán lại tiền nong trước khi hiển thị
        return "redirect:/cart"; 
    }

    // =========================================================
    // 3. CÁC CHỨC NĂNG KHÁC (Xóa, Checkout, Yêu thích...)
    // =========================================================

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("cartItemId") Long cartItemId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        cartItemRepository.findById(cartItemId).ifPresent(item -> {
            if (item.getUser().getId().equals(user.getId())) {
                cartItemRepository.delete(item);
            }
        });
        return "redirect:/cart"; // Xóa xong load lại trang Cart để cập nhật tiền
    }

    @PostMapping("/cart/checkout")
    public String checkout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<CartItem> items = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(items);

        return "redirect:/home";
    }

    // --- YÊU THÍCH ---
    @GetMapping("/favorites")
    public String viewFavorites(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<Favorite> favorites = favoriteRepository.findByUser(user);
        model.addAttribute("favorites", favorites);
        return "favorite"; 
    }

    @PostMapping("/favorites/toggle")
    public String toggleFavorite(@RequestParam("productId") Long productId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            // 1. Tìm tất cả các dòng trùng lặp (trả về List)
            List<Favorite> existList = (List<Favorite>) favoriteRepository.findByUserAndProduct_Id(user, productId);

            if (!existList.isEmpty()) {
                // 2. Nếu có (dù 1 hay nhiều dòng) -> XÓA HẾT
                favoriteRepository.deleteAll(existList);
            } else {
                // 3. Nếu chưa có -> THÊM MỚI
                Favorite newFav = new Favorite();
                newFav.setUser(user);
                newFav.setProduct(product);
                favoriteRepository.save(newFav);
            }
        }
        return "redirect:/favorites"; 
    }
}