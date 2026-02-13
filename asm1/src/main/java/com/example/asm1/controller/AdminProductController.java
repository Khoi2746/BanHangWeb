package com.example.asm1.controller;

import com.example.asm1.Entity.Product; // Nhớ sửa import theo đúng Entity của em
import com.example.asm1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;

    // 1. Hiển thị danh sách & Form thêm mới
    @GetMapping("")
    public String listProducts(Model model) {
        model.addAttribute("activePage", "products");
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("product", new Product()); // Form rỗng để thêm mới
        return "admin/product-management"; // Tên file HTML
    }

    // 2. Xử lý Lưu (Thêm mới / Sửa)
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        // Lưu thẳng vào DB (Spring Data JPA tự hiểu: có ID là update, chưa có là insert)
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // 3. Xử lý khi bấm nút Edit
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        Product p = productRepository.findById(id).orElse(new Product());
        model.addAttribute("product", p); // Đẩy dữ liệu cũ lên form
        model.addAttribute("products", productRepository.findAll()); // Vẫn hiện danh sách bên phải
        return "admin/product-management";
    }

    // 4. Xử lý Xóa
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}