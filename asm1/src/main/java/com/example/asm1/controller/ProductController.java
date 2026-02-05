package com.example.asm1.controller;

import com.example.asm1.Entity.Product;
import com.example.asm1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // ✅ Trang danh sách sản phẩm (Mặc định)
    @GetMapping("/new-arrivals")
    public String showNewArrivalsPage(Model model) {

        List<Product> listProducts = productRepository.findAll();

        model.addAttribute("products", listProducts);
        model.addAttribute("totalItems", listProducts.size());

        return "NewArrival"; // Tên file HTML của ku em
    }

    // ✅ Trang chi tiết sản phẩm
    @GetMapping("/product/detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {

        // 1. Tìm sản phẩm theo ID
        Product product = productRepository.findById(id).orElse(null);

        // 2. Nếu không tìm thấy → quay về trang list
        if (product == null) {
            return "redirect:/new-arrivals";
        }

        // 3. Gửi sản phẩm sang HTML
        model.addAttribute("product", product);

        // 4. Trả về trang chi tiết
        return "ProductDetail";
    }

    // 👇👇👇 PHẦN THÊM MỚI: CHỨC NĂNG TÌM KIẾM 👇👇👇

    @GetMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        
        // 1. Gọi Repository để tìm sản phẩm có tên chứa từ khóa (không phân biệt hoa thường)
        List<Product> searchResults = productRepository.findByNameContainingIgnoreCase(keyword);

        // 2. Gửi danh sách kết quả sang HTML (Tái sử dụng biến "products")
        model.addAttribute("products", searchResults);
        
        // 3. Gửi số lượng tìm thấy
        model.addAttribute("totalItems", searchResults.size());

        // 4. Gửi lại từ khóa để hiển thị tiêu đề (VD: Kết quả cho "Nike")
        model.addAttribute("searchKeyword", keyword);

        // 5. Trả về trang NewArrival để hiển thị danh sách như bình thường
        return "NewArrival";
    }
}