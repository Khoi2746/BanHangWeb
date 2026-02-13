package com.example.asm1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminStatisticsController {

    @GetMapping("/statistics")
    public String showStatistics() {
        
        return "admin/statistics"; // Trỏ về file templates/admin/statistics.html
    }
}