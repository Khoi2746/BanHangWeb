// package com.example.asm1.controller;

// // import com.example.asm1.Entity.Product;
// // import org.springframework.stereotype.Controller;
// // import org.springframework.ui.Model;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.RequestMapping;

// // import java.util.ArrayList;
// // import java.util.List;

// @Controller
// @RequestMapping("/cart")
// public class CartController {

//     // @GetMapping
//     // public String showCart(Model model) {
//     //     // 1. Tạo giả danh sách sản phẩm trong giỏ (Sau này lấy từ Session hoặc DB)
//     //     List<Product> cartItems = new ArrayList<>();
        
//     //     // --- DATA GIẢ (Ku em muốn test giỏ rỗng thì comment 2 dòng dưới lại) ---
//     //     // cartItems.add(new Product(1, "Nike Air Force 1 '07", "Men's Shoes", 3519000.0, "shoes1.jpg"));
//     //     // cartItems.add(new Product(2, "Nike Dri-FIT", "T-Shirt", 850000.0, "shirt1.jpg"));
//     //     // -----------------------------------------------------------------------

//     //     // 2. Tính tổng tiền
//     //     double subtotal = 0;
//     //     for (Product p : cartItems) {
//     //         subtotal += p.getPrice(); // Giả sử số lượng là 1
//     //     }

//     //     // 3. Gửi dữ liệu ra View
//     //     model.addAttribute("cartItems", cartItems);
//     //     model.addAttribute("subtotal", subtotal);
//     //     model.addAttribute("total", subtotal); // Tổng = Tạm tính (vì ship free)

//     //     return "cart"; // Trả về file cart.html
//     // }
// }