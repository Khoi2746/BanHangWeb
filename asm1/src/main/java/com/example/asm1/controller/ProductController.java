package com.example.asm1.controller;

import com.example.asm1.Entity.Product;
import com.example.asm1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/") // Định tuyến gốc
public class ProductController {

    // Tiêm (Inject) Repository để thao tác với Database
    @Autowired
    private ProductRepository productRepository;

    // Khi người dùng gõ: http://localhost:8080/new-arrivals
    @GetMapping("/new-arrivals")
    public String showNewArrivalsPage(Model model) {
        
        // 1. Lấy toàn bộ danh sách sản phẩm từ SQL lên
        List<Product> listProducts = productRepository.findAll();

        // 2. Đóng gói dữ liệu để gửi sang file HTML
        // "products": là tên biến dùng trong th:each="product : ${products}"
        model.addAttribute("products", listProducts);
        
        // "totalItems": để hiển thị số lượng New (1057) trên header
        model.addAttribute("totalItems", listProducts.size());

        // 3. Trả về tên file HTML (Lưu ý: Không cần đuôi .html)
        // Spring Boot sẽ tự tìm file này trong thư mục: src/main/resources/templates/
        return "NewArrival"; 
    }
}