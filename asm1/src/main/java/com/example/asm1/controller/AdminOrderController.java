package com.example.asm1.controller;

import com.example.asm1.Entity.Order;
import com.example.asm1.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("")
    public String listOrders(Model model) {
        model.addAttribute("activePage", "orders");
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/order-management"; 
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        return "admin/order-detail"; // ĐÃ SỬA: Phải có admin/ ở đầu
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam("orderId") Long orderId, 
                               @RequestParam("newStatus") String newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(newStatus);
        orderRepository.save(order);
        
        // ĐÃ SỬA: Redirect về đúng link detail của admin
        return "redirect:/admin/orders/detail/" + orderId;
    }
}