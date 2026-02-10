package com.example.asm1.controller;

import com.example.asm1.Entity.CartItem;
import com.example.asm1.Entity.CheckOutForm;
import com.example.asm1.Entity.User;
import com.example.asm1.service.CartService;

import jakarta.servlet.http.HttpSession; // Nhớ import cái này
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String viewCheckoutPage(Model model, HttpSession session) {
        
        // 1. Lấy User từ Session (Để biết ai đang mua hàng)
        User currentUser = (User) session.getAttribute("loggedInUser");

        // Nếu chưa đăng nhập -> Đá về trang login ngay
        if (currentUser == null) {
            return "redirect:/login";
        }

        // 2. Lấy Giỏ Hàng của ĐÚNG USER ĐÓ (Truyền currentUser vào)
        List<CartItem> items = cartService.getCartItems(currentUser); 
        
        // Nếu giỏ rỗng -> Đá về trang cart
        if (items.isEmpty()) {
            return "redirect:/cart";
        }

        // 3. Lấy tổng tiền (Gọi hàm Service đã viết cho gọn và chính xác)
        long total = cartService.getCartTotal(currentUser);

        // 4. Đẩy dữ liệu ra View
        model.addAttribute("cartItems", items);
        model.addAttribute("subtotal", total);
        model.addAttribute("total", total); 

        // 5. Tạo Form
        CheckOutForm form = new CheckOutForm();
        // Tự động điền thông tin có sẵn của user vào form cho tiện
        form.setFirstName(currentUser.getFirstName()); 
        form.setLastName(currentUser.getSurname());
        form.setEmail(currentUser.getEmail());
        // form.setPhoneNumber(currentUser.getPhoneNumber()); // Nếu trong User có sđt
        
        model.addAttribute("orderForm", form);

        return "checkout";
    }
}