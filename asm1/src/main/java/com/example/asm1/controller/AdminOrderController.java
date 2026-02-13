package com.example.asm1.controller;

import com.example.asm1.Entity.Order;
import com.example.asm1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    // 1. Xem danh sách tất cả đơn hàng
    @GetMapping("")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/order-management";
    }

    // 2. Xem chi tiết một đơn hàng (Ai mua cái gì)
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow();
        model.addAttribute("order", order);
        return "admin/order-detail"; // Tạo thêm 1 trang detail nếu muốn xem sp cụ thể
    }
}