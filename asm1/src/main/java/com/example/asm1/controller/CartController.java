// package com.example.asm1.controller;

// import com.example.asm1.Entity.CartItem;
// import com.example.asm1.Entity.User;
// import com.example.asm1.repository.CartItemRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import jakarta.servlet.http.HttpSession; // Nếu lỗi thì đổi thành javax.servlet.http.HttpSession
// import java.util.List;

// @Controller
// @RequestMapping("/cart")
// public class CartController {

//     @Autowired
//     private CartItemRepository cartItemRepository;

//     // 1. HIỂN THỊ GIỎ HÀNG
//     @GetMapping
//     public String showCart(Model model, HttpSession session) {
//         // Lấy user từ session
//         User user = (User) session.getAttribute("loggedInUser");
        
//         // Nếu chưa đăng nhập thì đá về trang login
//         if (user == null) {
//             return "redirect:/login";
//         }

//         // Lấy danh sách sản phẩm trong giỏ của User đó
//         List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());

//         // Tính tổng tiền
//         double total = 0;
//         for (CartItem item : cartItems) {
//             total += item.getProduct().getPrice() * item.getQuantity();
//         }

//         model.addAttribute("cartItems", cartItems);
//         model.addAttribute("subtotal", total);
//         model.addAttribute("total", total); // Ở đây anh để ship = 0, nếu có ship thì cộng thêm

//         return "cart"; // Trả về cart.html
//     }

//     // 2. XỬ LÝ TĂNG / GIẢM SỐ LƯỢNG
//     @GetMapping("/update/{id}/{action}")
//     public String updateQuantity(@PathVariable("id") Long cartItemId,
//                                  @PathVariable("action") String action) {
        
//         // Tìm dòng trong giỏ hàng cần sửa
//         CartItem item = cartItemRepository.findById(cartItemId).orElse(null);

//         if (item != null) {
//             // Logic TĂNG
//             if ("increase".equals(action)) {
//                 item.setQuantity(item.getQuantity() + 1);
//             } 
//             // Logic GIẢM
//             else if ("decrease".equals(action)) {
//                 if (item.getQuantity() > 1) {
//                     item.setQuantity(item.getQuantity() - 1);
//                 }
//                 // Nếu số lượng đang là 1 mà bấm giảm thì giữ nguyên (hoặc em muốn xóa thì gọi hàm delete)
//             }
            
//             // Lưu thay đổi vào DB
//             cartItemRepository.save(item);
//         }

//         // Load lại trang giỏ hàng để cập nhật giá tiền
//         return "redirect:/cart";
//     }

//     // 3. XÓA SẢN PHẨM KHỎI GIỎ
//     @PostMapping("/remove")
//     public String removeFromCart(@RequestParam("cartItemId") Long cartItemId) {
//         cartItemRepository.deleteById(cartItemId);
//         return "redirect:/cart";
//     }
// }